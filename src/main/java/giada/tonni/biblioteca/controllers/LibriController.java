package giada.tonni.biblioteca.controllers;

import giada.tonni.biblioteca.entities.Libro;
import giada.tonni.biblioteca.exceptions.ValidationException;
import giada.tonni.biblioteca.payloads.LibroDTO;
import giada.tonni.biblioteca.services.LibriService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/libri")
public class LibriController {

    private final LibriService libriService;

    public LibriController(LibriService libriService) {
        this.libriService = libriService;
    }

    @GetMapping
    public Page<Libro> getLibri(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "titolo") String orderBy,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String titolo,
            @RequestParam(required = false) String autore,
            @RequestParam(required = false) Integer annoPubblicazione
    ) {
        return this.libriService.findLibri(page, size, orderBy, isbn, titolo, autore, annoPubblicazione);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Libro postLibro(@RequestPart("libro") @Validated LibroDTO body, @RequestPart("copertina") MultipartFile file, BindingResult validationResults) {
        if (validationResults.hasErrors()) {
            List<String> errorsList = validationResults
                    .getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .toList();
            throw new ValidationException(errorsList);
        } else {
            return this.libriService.addLibro(body, file);
        }
    }

    @PutMapping("/{libroId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Libro putLibri(@PathVariable UUID libroId, @RequestBody @Validated LibroDTO body, BindingResult validationResults) {
        if (validationResults.hasErrors()) {
            List<String> errorsList = validationResults
                    .getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .toList();
            throw new ValidationException(errorsList);
        } else {
            return this.libriService.modificaLibro(libroId, body);
        }
    }

    @PatchMapping("/{libroId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Libro patchLibri(@PathVariable UUID libroId, @RequestParam("copertina") MultipartFile file) {
        return this.libriService.modificaCopertina(libroId, file);
    }

    @DeleteMapping("/{libroId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteLibro(@PathVariable UUID libroId) {
        this.libriService.deleteLibro(libroId);
    }

}
