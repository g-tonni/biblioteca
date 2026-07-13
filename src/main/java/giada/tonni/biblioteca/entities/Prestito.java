package giada.tonni.biblioteca.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "prestito")
public class Prestito {

    @Id
    @GeneratedValue
    private UUID prestitoId;

    @Column(nullable = false)
    private LocalDate dataInizio;

    @Column(nullable = false)
    private LocalDate dataScadenza;

    private LocalDate dataRestituzione;

    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "libro_id", nullable = false)
    private Libro libro;

    private Prestito() {
    }

    public Prestito(Utente utente, Libro libro) {
        this.dataInizio = LocalDate.now();
        this.dataScadenza = dataInizio.plusDays(30);
        this.utente = utente;
        this.libro = libro;
    }

    public UUID getPrestitoId() {
        return prestitoId;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public Utente getUtente() {
        return utente;
    }

    public Libro getLibro() {
        return libro;
    }

    public LocalDate getDataRestituzione() {
        return dataRestituzione;
    }

    public void setDataRestituzione(LocalDate dataRestituzione) {
        this.dataRestituzione = dataRestituzione;
    }
}
