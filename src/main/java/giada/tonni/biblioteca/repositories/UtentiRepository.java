package giada.tonni.biblioteca.repositories;

import giada.tonni.biblioteca.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UtentiRepository extends JpaRepository<Utente, UUID>, JpaSpecificationExecutor<Utente> {
    boolean existsByEmail(String email);

    boolean existsByTelefono(long telefono);

    Utente findByEmail(String email);
}
