package giada.tonni.biblioteca.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "utente")
@JsonIgnoreProperties({"password", "ruolo", "accountNonExpired", "accountNonLocked", "authorities", "credentialsNonExpired", "enabled"})
public class Utente implements UserDetails {

    @Id
    @GeneratedValue
    private UUID utenteId;

    @Column(length = 20, nullable = false)
    private String nome;

    @Column(length = 20, nullable = false)
    private String cognome;

    @Column(nullable = false)
    private LocalDate dataNascita;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private long telefono;

    @Column(nullable = false)
    private LocalDate dataIscrizione;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Ruolo ruolo;

    private Utente() {
    }

    public Utente(String nome, String cognome, LocalDate dataNascita, String email, String password, long telefono, Ruolo ruolo) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.ruolo = ruolo;
        this.dataIscrizione = LocalDate.now();
    }


    public UUID getUtenteId() {
        return utenteId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public LocalDate getDataIscrizione() {
        return dataIscrizione;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.ruolo.getRuolo()));
    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return this.nome;
    }
}
