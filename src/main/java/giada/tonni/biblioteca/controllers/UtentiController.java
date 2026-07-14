package giada.tonni.biblioteca.controllers;

import giada.tonni.biblioteca.entities.Utente;
import giada.tonni.biblioteca.exceptions.ValidationException;
import giada.tonni.biblioteca.payloads.PutUtenteDTO;
import giada.tonni.biblioteca.payloads.UtenteDTO;
import giada.tonni.biblioteca.services.UtentiService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/utenti")
public class UtentiController {

    public final UtentiService utentiService;

    public UtentiController(UtentiService utentiService) {
        this.utentiService = utentiService;
    }

    @GetMapping
    public Page<Utente> getUtenti(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "cognome") String orderBy,
            @RequestParam(required = false) String cognome,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Long telefono
    ) {
        return this.utentiService.findUtenti(page, size, orderBy, cognome, email, telefono);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Utente postUtente(@RequestBody @Validated UtenteDTO body, BindingResult validationResults) {
        if (validationResults.hasErrors()) {
            List<String> listaErrori = validationResults.getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new ValidationException(listaErrori);
        } else {
            return this.utentiService.addUtente(body);
        }
    }

    @PutMapping("/{utenteId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Utente putUtente(@PathVariable UUID utenteId, @RequestBody @Validated PutUtenteDTO body, BindingResult validationResults) {
        if (validationResults.hasErrors()) {
            List<String> listaErrori = validationResults.getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new ValidationException(listaErrori);
        } else {
            return this.utentiService.putUtente(utenteId, body);
        }
    }

    @DeleteMapping("/{utenteId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteUtente(@PathVariable UUID utenteId) {
        this.utentiService.deleteUtente(utenteId);
    }
}
