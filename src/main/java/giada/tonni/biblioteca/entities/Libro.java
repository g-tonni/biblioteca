package giada.tonni.biblioteca.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "libro")
public class Libro {

    @Id
    @GeneratedValue
    private UUID libroId;

    @Column(nullable = false)
    private String isbn;

    @Column(length = 100, nullable = false)
    private String titolo;

    @Column(length = 800, nullable = false)
    private String descrizione;

    @Column(nullable = false)
    private int numPagine;

    @Column(nullable = false)
    private String copertina;

    @Column(nullable = false)
    private int annoPubblicazione;

    @ManyToOne
    @JoinColumn(name = "autore_id", nullable = false)
    private Autore autore;

    private Libro() {
    }

    public Libro(String isbn, String titolo, String descrizione, int numPagine, String copertina, int annoPubblicazione, Autore autore) {
        this.isbn = isbn;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.numPagine = numPagine;
        this.copertina = copertina;
        this.annoPubblicazione = annoPubblicazione;
        this.autore = autore;
    }

    public UUID getLibroId() {
        return libroId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getNumPagine() {
        return numPagine;
    }

    public void setNumPagine(int numPagine) {
        this.numPagine = numPagine;
    }

    public String getCopertina() {
        return copertina;
    }

    public void setCopertina(String copertina) {
        this.copertina = copertina;
    }

    public int getAnnoPubblicazione() {
        return annoPubblicazione;
    }

    public void setAnnoPubblicazione(int annoPubblicazione) {
        this.annoPubblicazione = annoPubblicazione;
    }

    public Autore getAutore() {
        return autore;
    }

    public void setAutore(Autore autore) {
        this.autore = autore;
    }
}
