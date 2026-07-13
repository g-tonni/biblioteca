package giada.tonni.biblioteca.payloads;

import java.util.UUID;

public record LoginResponseDTO(
        String token,
        UUID userId
) {
}
