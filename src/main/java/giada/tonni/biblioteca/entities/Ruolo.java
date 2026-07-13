package giada.tonni.biblioteca.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ruolo")
public class Ruolo {

    @Id
    private String ruolo;

    public Ruolo() {
    }

    public Ruolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public String getRuolo() {
        return ruolo;
    }
}
