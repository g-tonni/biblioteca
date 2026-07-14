package giada.tonni.biblioteca.specifications;

import giada.tonni.biblioteca.entities.Prestito;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PrestitiSpecification {

    public Specification<Prestito> findByTitoloLibro(String titolo) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("libro").get("titolo")), "%" + titolo.toLowerCase() + "%"));
    }

    public Specification<Prestito> findByEmailUtente(String email) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("utente").get("email")), "%" + email.toLowerCase() + "%"));
    }

    public Specification<Prestito> findByCognomeUtente(String cognome) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("utente").get("cognome")), "%" + cognome.toLowerCase() + "%"));
    }

    public Specification<Prestito> findByDataScadenza(LocalDate dataScadenza) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("dataScadenza"), dataScadenza));
    }

    public Specification<Prestito> findByDataInizio(LocalDate dataInizio) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("dataInizio"), dataInizio));
    }

    public Specification<Prestito> findNonRestituiti() {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.isNull(root.get("dataRestituzione")));
    }

    public Specification<Prestito> findScaduti() {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.lessThan(root.get("dataScadenza"), LocalDate.now()),
                        criteriaBuilder.isNull(root.get("dataRestituzione"))));
    }
}
