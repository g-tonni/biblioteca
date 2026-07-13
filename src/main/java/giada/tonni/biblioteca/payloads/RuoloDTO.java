package giada.tonni.biblioteca.payloads;

import jakarta.validation.constraints.NotBlank;

public record RuoloDTO(
        @NotBlank(message = "Il ruolo deve essere inserito")
        String ruolo
) {
}
