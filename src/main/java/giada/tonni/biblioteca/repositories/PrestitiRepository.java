package giada.tonni.biblioteca.repositories;

import giada.tonni.biblioteca.entities.Prestito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PrestitiRepository extends JpaRepository<Prestito, UUID>, JpaSpecificationExecutor<Prestito> {
    boolean existsByLibroLibroIdAndDataRestituzioneIsNull(UUID libroId);

    List<Prestito> findByUtenteUtenteIdAndDataRestituzioneIsNull(UUID utenteId);
}
