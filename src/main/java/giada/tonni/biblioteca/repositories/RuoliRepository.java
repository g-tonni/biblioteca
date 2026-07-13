package giada.tonni.biblioteca.repositories;

import giada.tonni.biblioteca.entities.Ruolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuoliRepository extends JpaRepository<Ruolo, String> {
    boolean existsById(String ruolo);
}
