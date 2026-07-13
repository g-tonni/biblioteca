package giada.tonni.biblioteca.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AutoreDTO(

        @NotBlank(message = "Il nome dell'autore deve essere inserito")
        @Size(min = 2, max = 70, message = "Il nome dell'autore deve essere compreso tra 2 e 70 caratteri")
        String autore

) {
}
