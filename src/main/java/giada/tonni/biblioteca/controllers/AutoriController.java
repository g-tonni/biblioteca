package giada.tonni.biblioteca.controllers;

import giada.tonni.biblioteca.entities.Autore;
import giada.tonni.biblioteca.exceptions.ValidationException;
import giada.tonni.biblioteca.payloads.AutoreDTO;
import giada.tonni.biblioteca.payloads.DeleteAutoreDTO;
import giada.tonni.biblioteca.services.AutoriService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autori")
public class AutoriController {

    private final AutoriService autoriService;

    public AutoriController(AutoriService autoriService) {
        this.autoriService = autoriService;
    }

    @GetMapping
    public List<Autore> getAutori() {
        return this.autoriService.findAllAutori();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
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

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteAutore(@RequestBody DeleteAutoreDTO body) {
        this.autoriService.deleteAutore(body);
    }
}
