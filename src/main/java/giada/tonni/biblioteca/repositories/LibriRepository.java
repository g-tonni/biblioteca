package giada.tonni.biblioteca.repositories;

import giada.tonni.biblioteca.entities.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LibriRepository extends JpaRepository<Libro, UUID>, JpaSpecificationExecutor<Libro> {

    boolean existsByIsbn(String isbn);

    Libro findByIsbn(String isbn);

}
