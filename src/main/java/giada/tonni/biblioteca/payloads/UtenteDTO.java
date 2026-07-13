package giada.tonni.biblioteca.payloads;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UtenteDTO(

        @NotBlank(message = "Il nome deve essere inserito")
        @Size(min = 2, max = 20, message = "Il nome deve essere compreso tra 2 e 20 caratteri")
        String nome,

        @NotBlank(message = "Il cognome deve essere inserito")
        @Size(min = 2, max = 20, message = "Il cognome deve essere compreso tra 2 e 20 caratteri")
        String cognome,

        @NotNull(message = "La data di nascita deve essere inserita")
        @Past(message = "La data di nascita deve essere una data passata")
        LocalDate dataNascita,

        @NotBlank(message = "L'email deve essere inserita")
        @Email(message = "Indirizzo email non valido")
        String email,

        @Pattern(
                regexp = "^$|^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "La password deve contenere almeno 8 caratteri, una lettera maiuscola, una lettera minuscola, un numero e un carattere speciale"
        )
        String password,

        @NotNull(message = "Il numero di telefono deve essere inserito")
        long telefono

) {

}
