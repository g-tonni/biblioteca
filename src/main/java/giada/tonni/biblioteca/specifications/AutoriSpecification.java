package giada.tonni.biblioteca.specifications;

import giada.tonni.biblioteca.entities.Autore;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AutoriSpecification {

    public Specification<Autore> findAutoreByPartialName(String partialName) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("autore")), "%" + partialName.toLowerCase() + "%"));
    }
}
