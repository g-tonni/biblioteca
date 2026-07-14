package giada.tonni.biblioteca.specifications;

import giada.tonni.biblioteca.entities.Utente;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class UtentiSpecification {

    public Specification<Utente> findUtenteByCognome(String cognomeParziale) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("cognome")), "%" + cognomeParziale.toLowerCase() + "%"));
    }

    public Specification<Utente> findUtenteByEmail(String emailParziale) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + emailParziale.toLowerCase() + "%"));
    }

    public Specification<Utente> findUtenteByTelefono(long telefonoParziale) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("telefono").as(String.class), "%" + telefonoParziale + "%"));
    }
}
