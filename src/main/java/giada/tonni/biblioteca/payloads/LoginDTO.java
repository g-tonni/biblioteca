package giada.tonni.biblioteca.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO(

        @NotBlank(message = "L'email deve essere inserita")
        @Email(message = "Indirizzo email non valido")
        String email,

        @NotBlank(message = "La password deve essere inserita")
        String password
) {
}
