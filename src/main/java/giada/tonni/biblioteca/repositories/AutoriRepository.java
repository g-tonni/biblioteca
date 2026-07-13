package giada.tonni.biblioteca.repositories;

import giada.tonni.biblioteca.entities.Autore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AutoriRepository extends JpaRepository<Autore, UUID>, JpaSpecificationExecutor<Autore> {

    boolean existsByAutore(String autore);
}
