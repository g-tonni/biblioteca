package giada.tonni.biblioteca.services;

import giada.tonni.biblioteca.entities.Ruolo;
import giada.tonni.biblioteca.entities.Utente;
import giada.tonni.biblioteca.exceptions.BadRequestException;
import giada.tonni.biblioteca.exceptions.NotFoundException;
import giada.tonni.biblioteca.payloads.PutUtenteDTO;
import giada.tonni.biblioteca.payloads.UtenteDTO;
import giada.tonni.biblioteca.repositories.UtentiRepository;
import giada.tonni.biblioteca.specifications.UtentiSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UtentiService {

    private final UtentiRepository utentiRepository;
    private final RuoliService ruoliService;
    private final UtentiSpecification utentiSpecification;
    private final PasswordEncoder passwordEncoder;

    public UtentiService(UtentiRepository utentiRepository, RuoliService ruoliService, UtentiSpecification utentiSpecification, PasswordEncoder passwordEncoder) {
        this.utentiRepository = utentiRepository;
        this.ruoliService = ruoliService;
        this.utentiSpecification = utentiSpecification;
        this.passwordEncoder = passwordEncoder;
    }

    public Utente findUtenteById(UUID utenteId) {
        return this.utentiRepository.findById(utenteId).orElseThrow(() -> new NotFoundException(utenteId));
    }

    public Utente findUtenteByEmail(String email) {
        System.out.println("Email: " + email);
        Utente utenteTrovato = this.utentiRepository.findByEmail(email);
        System.out.println("Utente: " + utenteTrovato);
        if (utenteTrovato == null) {
            throw new BadRequestException("Non esiste un utente associato a questa email");
        } else {
            return utenteTrovato;
        }
    }

    public List<Utente> findAllUtenti() {
        return this.utentiRepository.findAll();
    }

    public Page<Utente> findUtenti(int page, int size, String orderBy, String cognome, String email, Long telefono) {
        if (page < 0) page = 0;
        if (size < 0 || size > 20) size = 0;

        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));

        Specification<Utente> spec = (root, query, cb) -> cb.conjunction();

        if (cognome != null) {
            spec = spec.and(this.utentiSpecification.findUtenteByCognome(cognome));
        }
        if (email != null) {
            spec = spec.and(this.utentiSpecification.findUtenteByEmail(email));
        }
        if (telefono != null) {
            spec = spec.and(this.utentiSpecification.findUtenteByTelefono(telefono));
        }

        return this.utentiRepository.findAll(spec, pageable);
    }

    public Utente addUtente(UtenteDTO body) {

        if (this.utentiRepository.existsByEmail(body.email())) throw new BadRequestException("Email esistente");
        if (this.utentiRepository.existsByTelefono(body.telefono()))
            throw new BadRequestException("Telefono esistente");

        Ruolo ruolo = this.ruoliService.findRuoloById("ROLE_UTENTE");

        Utente nuovoUtente = new Utente(body.nome(), body.cognome(), body.dataNascita(), body.email(), passwordEncoder.encode(body.password()), body.telefono(), ruolo);

        this.utentiRepository.save(nuovoUtente);

        System.out.println("Utente salvato");

        return nuovoUtente;
    }

    public Utente addFirstAdmin(UtenteDTO body) {

        Ruolo ruolo = this.ruoliService.findRuoloById("ROLE_ADMIN");

        Utente admin = new Utente(body.nome(), body.cognome(), body.dataNascita(), body.email(), passwordEncoder.encode(body.password()), body.telefono(), ruolo);

        this.utentiRepository.save(admin);

        System.out.println("Admin salvato");

        return admin;
    }

    public Utente putUtente(UUID utenteId, PutUtenteDTO body) {

        Utente utenteTrovato = this.findUtenteById(utenteId);

        if (!body.email().equals(utenteTrovato.getEmail())) {
            if (this.utentiRepository.existsByEmail(body.email())) throw new BadRequestException("Email esistente");
        }
        if (body.telefono() != utenteTrovato.getTelefono()) {
            if (this.utentiRepository.existsByTelefono(body.telefono()))
                throw new BadRequestException("Telefono esistente");
        }

        utenteTrovato.setNome(body.nome());
        utenteTrovato.setCognome(body.cognome());
        utenteTrovato.setDataNascita(body.dataNascita());
        utenteTrovato.setEmail(body.email());
        utenteTrovato.setTelefono(body.telefono());

        this.utentiRepository.save(utenteTrovato);

        System.out.println("Utente modificato correttamente");

        return utenteTrovato;
    }

    public void deleteUtente(UUID utenteId) {

        Utente utenteTrovato = this.findUtenteById(utenteId);

        this.utentiRepository.delete(utenteTrovato);

        System.out.println("Utente eliminato");
    }
}
