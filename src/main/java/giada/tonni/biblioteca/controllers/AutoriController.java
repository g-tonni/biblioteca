package giada.tonni.biblioteca.controllers;

import giada.tonni.biblioteca.entities.Autore;
import giada.tonni.biblioteca.exceptions.ValidationException;
import giada.tonni.biblioteca.payloads.AutoreDTO;
import giada.tonni.biblioteca.services.AutoriService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/autori")
public class AutoriController {

    private final AutoriService autoriService;

    public AutoriController(AutoriService autoriService) {
        this.autoriService = autoriService;
    }

    @GetMapping
    public Page<Autore> getAutori(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String partialName
    ) {
        return this.autoriService.findAllAutori(page, size, partialName);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Autore addAutore(@RequestBody @Validated AutoreDTO body, BindingResult validationResults) {
        if (validationResults.hasErrors()) {
            List<String> listaErrori = validationResults
                    .getFieldErrors()
                    .stream()
                    .map(errore -> errore.getDefaultMessage())
                    .toList();
            throw new ValidationException(listaErrori);
        } else {
            return this.autoriService.saveAutore(body);
        }
    }

    @DeleteMapping("/{autoreId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteAutore(@PathVariable UUID autoreId) {
        this.autoriService.deleteAutore(autoreId);
    }
}
