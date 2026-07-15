package giada.tonni.biblioteca.controllers;

import giada.tonni.biblioteca.entities.Prestito;
import giada.tonni.biblioteca.exceptions.ValidationException;
import giada.tonni.biblioteca.payloads.PrestitoDTO;
import giada.tonni.biblioteca.services.PrestitiService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/prestiti")
public class PrestitiController {

    private final PrestitiService prestitiService;

    public PrestitiController(PrestitiService prestitiService) {
        this.prestitiService = prestitiService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Page<Prestito> getPrestiti(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dataInizio") String orderBy,
            @RequestParam(required = false) String titolo,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String cognome,
            @RequestParam(required = false) LocalDate dataScadenza,
            @RequestParam(required = false) LocalDate dataInizio,
            @RequestParam(required = false) Boolean soloNonRestituiti,
            @RequestParam(required = false) Boolean soloScaduti
    ) {
        return this.prestitiService.getPrestiti(page, size, orderBy, titolo, email, cognome, dataScadenza, dataInizio, soloNonRestituiti, soloScaduti);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Prestito postPrestito(@RequestBody @Validated PrestitoDTO body, BindingResult validationResults) {
        if (validationResults.hasErrors()) {
            List<String> errorsList = validationResults
                    .getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .toList();
            throw new ValidationException(errorsList);
        } else {
            return this.prestitiService.addPrestito(body);
        }
    }

    @PutMapping("/{prestitoId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Prestito libroRestituito(@PathVariable UUID prestitoId) {
        return this.prestitiService.libroRestituito(prestitoId);
    }

    @DeleteMapping("/{prestitoId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteLibro(@PathVariable UUID prestitoId) {
        this.prestitiService.deletePrestito(prestitoId);
    }

}
