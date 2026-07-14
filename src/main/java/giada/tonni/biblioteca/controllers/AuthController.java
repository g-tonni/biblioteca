package giada.tonni.biblioteca.controllers;

import giada.tonni.biblioteca.payloads.LoginDTO;
import giada.tonni.biblioteca.payloads.LoginResponseDTO;
import giada.tonni.biblioteca.services.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO body) {
        return this.authService.checkCredentialsAndGenerateToken(body);
    }
}
