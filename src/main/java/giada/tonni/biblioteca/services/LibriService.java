package giada.tonni.biblioteca.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import giada.tonni.biblioteca.entities.Autore;
import giada.tonni.biblioteca.entities.Libro;
import giada.tonni.biblioteca.exceptions.BadRequestException;
import giada.tonni.biblioteca.exceptions.NotFoundException;
import giada.tonni.biblioteca.payloads.LibroDTO;
import giada.tonni.biblioteca.repositories.LibriRepository;
import giada.tonni.biblioteca.specifications.LibriSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class LibriService {

    private final LibriRepository libriRepository;
    private final LibriSpecification libriSpecification;
    private final AutoriService autoriService;
    private final Cloudinary cloudinaryUploader;

    public LibriService(LibriRepository libriRepository, LibriSpecification libriSpecification, AutoriService autoriService, Cloudinary cloudinaryUploader) {
        this.libriRepository = libriRepository;
        this.libriSpecification = libriSpecification;
        this.autoriService = autoriService;
        this.cloudinaryUploader = cloudinaryUploader;
    }

    public Libro findLibroById(UUID libroId) {
        return this.libriRepository.findById(libroId).orElseThrow(() -> new NotFoundException(libroId));
    }

    public Libro findLibroByIsbn(String isbn) {
        Libro libroTrovato = this.findLibroByIsbn(isbn);

        if (libroTrovato == null) {
            throw new BadRequestException("Non esiste un libro associato a questo ISBN");
        } else {
            return libroTrovato;
        }
    }

    public Page<Libro> findLibri(int page, int size, String orderBy, String isbn, String titolo, String autore, Integer annoPubblicazione) {
        if (page < 0) page = 0;
        if (size < 0 || size > 20) size = 0;

        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));

        Specification<Libro> spec = (root, query, cb) -> cb.conjunction();

        if (isbn != null) {
            spec = spec.and(this.libriSpecification.findByPartialIsbn(isbn));
        }

        if (titolo != null) {
            spec = spec.and(this.libriSpecification.findByPartialTitle(titolo));
        }

        if (autore != null) {
            spec = spec.and(this.libriSpecification.findByPartialAutor(autore));
        }

        if (annoPubblicazione != null) {
            spec = spec.and(this.libriSpecification.findByAnnoPubblicazione(annoPubblicazione));
        }

        return this.libriRepository.findAll(spec, pageable);
    }

    public Libro addLibro(LibroDTO body, MultipartFile file) {
        if (this.libriRepository.existsByIsbn(body.isbn())) {
            throw new BadRequestException("L'ISBN è esistente");
        }

        Autore autore = this.autoriService.findAutoreById(body.autore());

        String imageUrl;

        try {
            Map result = cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            imageUrl = (String) result.get("secure_url");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Libro nuovoLibro = new Libro(body.isbn(), body.titolo(), body.descrizione(), body.numPagine(), imageUrl, body.annoPubblicazione(), autore);

        this.libriRepository.save(nuovoLibro);

        System.out.println("Libro salvato correttamente");

        return nuovoLibro;
    }

    public Libro modificaLibro(UUID libroId, LibroDTO body) {
        Libro libroTrovato = this.findLibroById(libroId);

        if (!libroTrovato.getIsbn().equals(body.isbn())) {
            if (this.libriRepository.existsByIsbn(body.isbn())) {
                throw new BadRequestException("L'ISBN è esistente");
            } else {

                libroTrovato.setIsbn(body.isbn());
            }
        }

        if (!libroTrovato.getAutore().getAutoreId().equals(body.autore())) {

            Autore autoreTrovato = this.autoriService.findAutoreById(body.autore());

            libroTrovato.setAutore(autoreTrovato);

        }

        libroTrovato.setTitolo(body.titolo());
        libroTrovato.setDescrizione(body.descrizione());
        libroTrovato.setNumPagine(body.numPagine());
        libroTrovato.setAnnoPubblicazione(body.annoPubblicazione());

        this.libriRepository.save(libroTrovato);

        System.out.println("Libro modificato correttamente");

        return libroTrovato;
    }

    public Libro modificaCopertina(UUID libroId, MultipartFile file) {

        Libro libroTrovato = this.findLibroById(libroId);

        try {
            Map result = cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) result.get("secure_url");
            libroTrovato.setCopertina(imageUrl);
            this.libriRepository.save(libroTrovato);
            System.out.println("Copertina libro aggiornata");
            return libroTrovato;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteLibro(UUID libroId) {
        Libro libroTrovato = this.findLibroById(libroId);

        this.libriRepository.delete(libroTrovato);

        System.out.println("Libro eliminato");
    }


}
