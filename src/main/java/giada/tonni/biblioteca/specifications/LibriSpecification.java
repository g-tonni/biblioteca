package giada.tonni.biblioteca.specifications;

import giada.tonni.biblioteca.entities.Libro;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class LibriSpecification {

    public Specification<Libro> findByPartialIsbn(String partialIsbn) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("titolo")), "%" + partialIsbn.toLowerCase() + "%"));
    }

    public Specification<Libro> findByPartialTitle(String partialTitle) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("titolo")), "%" + partialTitle.toLowerCase() + "%"));
    }

    public Specification<Libro> findByPartialAutor(String partialAutor) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("autore").get("autore")), "%" + partialAutor.toLowerCase() + "%"));
    }

    public Specification<Libro> findByAnnoPubblicazione(Integer anno) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("annoPubblicazione"), anno));
    }
}
