package giada.tonni.biblioteca.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LibroDTO(

        @NotBlank(message = "L'ISBN deve essere inserito")
        String isbn,

        @NotBlank(message = "Il titolo deve essere inserito")
        @Size(min = 2, max = 100, message = "Il titolo deve essere compreso tra 2 e 100 caratteri")
        String titolo,

        @NotBlank(message = "La descrizione deve essere inserita")
        @Size(min = 50, max = 800, message = "La descrizione deve essere compresa tra 50 e 800 caratteri")
        String descrizione,

        @NotNull(message = "Il numero delle pagine deve essere inserito")
        int numPagine,

        @NotBlank(message = "L'immagine di copertina deve essere inserita")
        String copertina,

        @NotNull(message = "L'anno di pubblicazione deve essere inserito")
        int annoPubblicazione,

        @NotNull(message = "L'id dell'autore deve essere inserito")
        String autore
) {
}
