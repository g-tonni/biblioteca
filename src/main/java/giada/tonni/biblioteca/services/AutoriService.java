package giada.tonni.biblioteca.services;

import giada.tonni.biblioteca.entities.Autore;
import giada.tonni.biblioteca.exceptions.BadRequestException;
import giada.tonni.biblioteca.exceptions.NotFoundException;
import giada.tonni.biblioteca.payloads.AutoreDTO;
import giada.tonni.biblioteca.payloads.DeleteAutoreDTO;
import giada.tonni.biblioteca.repositories.AutoriRepository;
import giada.tonni.biblioteca.specifications.AutoriSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AutoriService {

    private final AutoriRepository autoriRepository;
    private final AutoriSpecification autoriSpecification;

    public AutoriService(AutoriRepository autoriRepository, AutoriSpecification autoriSpecification) {
        this.autoriRepository = autoriRepository;
        this.autoriSpecification = autoriSpecification;
    }

    public Page<Autore> findAllAutori(int page, int size, String partialName) {

        if (size > 20 || size < 0) size = 10;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size);

        Specification<Autore> spec = (root, query, cb) -> cb.conjunction();

        if (partialName != null) {
            spec = spec.and(autoriSpecification.findAutoreByPartialName(partialName));
        }

        return this.autoriRepository.findAll(spec, pageable);
    }

    public Autore findAutoreById(UUID autoreId) {
        return this.autoriRepository.findById(autoreId).orElseThrow(() -> new NotFoundException(autoreId));
    }

    public Autore saveAutore(AutoreDTO body) {
        if (this.autoriRepository.existsByAutore(body.autore())) throw new BadRequestException("Autore esistente");

        Autore nuovoAutore = new Autore(body.autore());
        this.autoriRepository.save(nuovoAutore);
        System.out.println("Autore salvato");
        return nuovoAutore;
    }

    public void deleteAutore(DeleteAutoreDTO body) {
        Autore autore = this.findAutoreById(body.autoreId());

        this.autoriRepository.delete(autore);

        System.out.println("Autore eliminato");
    }
}
