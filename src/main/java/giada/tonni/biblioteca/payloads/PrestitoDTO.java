package giada.tonni.biblioteca.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record PrestitoDTO(
        @NotBlank(message = "L'email deve essere inserita")
        @Email(message = "Indirizzo email non valido")
        String emailUtente,

        @NotBlank(message = "L'id del libro deve essere inserito")
        UUID libroId
) {
}
