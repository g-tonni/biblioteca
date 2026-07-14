package giada.tonni.biblioteca.services;

import giada.tonni.biblioteca.entities.Libro;
import giada.tonni.biblioteca.entities.Prestito;
import giada.tonni.biblioteca.entities.Utente;
import giada.tonni.biblioteca.exceptions.BadRequestException;
import giada.tonni.biblioteca.exceptions.NotFoundException;
import giada.tonni.biblioteca.payloads.PrestitoDTO;
import giada.tonni.biblioteca.repositories.PrestitiRepository;
import giada.tonni.biblioteca.specifications.PrestitiSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PrestitiService {

    private final PrestitiRepository prestitiRepository;
    private final PrestitiSpecification prestitiSpecification;
    private final LibriService libriService;
    private final UtentiService utentiService;

    public PrestitiService(PrestitiRepository prestitiRepository, PrestitiSpecification prestitiSpecification, LibriService libriService, UtentiService utentiService) {
        this.prestitiRepository = prestitiRepository;
        this.prestitiSpecification = prestitiSpecification;
        this.libriService = libriService;
        this.utentiService = utentiService;
    }

    public Prestito findPrestitoById(UUID prestitoId) {
        return this.prestitiRepository.findById(prestitoId).orElseThrow(() -> new NotFoundException(prestitoId));
    }

    public Page<Prestito> getPrestiti(int page,
                                      int size,
                                      String orderBy,
                                      String titolo,
                                      String email,
                                      String cognome,
                                      LocalDate dataScadenza,
                                      LocalDate dataInizio,
                                      Boolean soloNonRestituiti,
                                      Boolean soloScaduti) {

        if (page < 0) page = 0;
        if (size < 0 || size > 20) size = 0;

        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));

        Specification<Prestito> spec = ((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());

        if (titolo != null) {
            spec = spec.and(this.prestitiSpecification.findByTitoloLibro(titolo));
        }
        if (email != null) {
            spec = spec.and(this.prestitiSpecification.findByEmailUtente(email));
        }
        if (cognome != null) {
            spec = spec.and(this.prestitiSpecification.findByCognomeUtente(cognome));
        }
        if (dataScadenza != null) {
            spec = spec.and(this.prestitiSpecification.findByDataScadenza(dataScadenza));
        }
        if (dataInizio != null) {
            spec = spec.and(this.prestitiSpecification.findByDataInizio(dataInizio));
        }
        if (Boolean.TRUE.equals(soloNonRestituiti)) {
            spec = spec.and(this.prestitiSpecification.findNonRestituiti());
        }
        if (Boolean.TRUE.equals(soloScaduti)) {
            spec = spec.and(this.prestitiSpecification.findScaduti());
        }

        return this.prestitiRepository.findAll(spec, pageable);
    }

    public Prestito addPrestito(PrestitoDTO body) {
        Libro libroTrovato = this.libriService.findLibroById(body.libroId());

        boolean libroNonDisponibile = this.prestitiRepository.existsByLibroLibroIdAndDataRestituzioneIsNull(body.libroId());

        if (libroNonDisponibile) {
            throw new BadRequestException("Libro non disponibile");
        }

        Utente utenteTrovato = this.utentiService.findUtenteByEmail(body.emailUtente());

        List<Prestito> prestitiInCorso = this.prestitiRepository.findByUtenteUtenteIdAndDataRestituzioneIsNull(utenteTrovato.getUtenteId());

        if (prestitiInCorso.size() > 3) {
            throw new BadRequestException("Ci sono più di 3 prestiti in corso per questo utente");
        }

        Prestito nuovoPrestito = new Prestito(utenteTrovato, libroTrovato);

        this.prestitiRepository.save(nuovoPrestito);

        System.out.println("Prestito salvato");

        return nuovoPrestito;
    }

    public Prestito libroRestituito(UUID prestitoId) {
        Prestito prestitoTrovato = this.findPrestitoById(prestitoId);

        prestitoTrovato.setDataRestituzione(LocalDate.now());

        this.prestitiRepository.save(prestitoTrovato);

        System.out.println("Data restituzione inserita");

        return prestitoTrovato;
    }

    public void deletePrestito(UUID prestitoId) {
        Prestito prestitoTrovato = this.findPrestitoById(prestitoId);
        this.prestitiRepository.delete(prestitoTrovato);
        System.out.println("Prestito eliminato");
    }
}
