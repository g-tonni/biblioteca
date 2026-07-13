package giada.tonni.biblioteca.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "autore")
public class Autore {

    @Id
    @GeneratedValue
    private UUID autoreId;

    @Column(length = 70, nullable = false)
    private String autore;

    public Autore() {
    }

    public Autore(String autore) {
        this.autore = autore;
    }

    public UUID getAutoreId() {
        return autoreId;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }
}
