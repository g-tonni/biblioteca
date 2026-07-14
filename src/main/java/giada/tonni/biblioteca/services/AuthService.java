package giada.tonni.biblioteca.services;

import giada.tonni.biblioteca.entities.Utente;
import giada.tonni.biblioteca.exceptions.UnauthorizedException;
import giada.tonni.biblioteca.payloads.LoginDTO;
import giada.tonni.biblioteca.payloads.LoginResponseDTO;
import giada.tonni.biblioteca.security.JWTTools;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UtentiService utentiService;
    private final PasswordEncoder passwordEncoder;
    private final JWTTools jwtTools;

    public AuthService(UtentiService utentiService, PasswordEncoder passwordEncoder, JWTTools jwtTools) {
        this.utentiService = utentiService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTools = jwtTools;
    }

    public LoginResponseDTO checkCredentialsAndGenerateToken(LoginDTO body) {
        Utente utenteTrovato = this.utentiService.findUtenteByEmail(body.email());
        if (passwordEncoder.matches(body.password(), utenteTrovato.getPassword())) {
            return new LoginResponseDTO(this.jwtTools.generateToken(utenteTrovato), utenteTrovato.getUtenteId());
        } else {
            throw new UnauthorizedException("Password errata");
        }
    }
}
