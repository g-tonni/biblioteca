package giada.tonni.biblioteca.services;

import giada.tonni.biblioteca.entities.Autore;
import giada.tonni.biblioteca.exceptions.BadRequestException;
import giada.tonni.biblioteca.exceptions.NotFoundException;
import giada.tonni.biblioteca.payloads.AutoreDTO;
import giada.tonni.biblioteca.payloads.DeleteAutoreDTO;
import giada.tonni.biblioteca.repositories.AutoriRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AutoriService {

    private final AutoriRepository autoriRepository;

    public AutoriService(AutoriRepository autoriRepository) {
        this.autoriRepository = autoriRepository;
    }

    public List<Autore> findAllAutori() {
        return this.autoriRepository.findAll();
    }

    public Autore findAutoreById(UUID autoreId) {
        return this.autoriRepository.findById(autoreId).orElseThrow(() -> new NotFoundException(autoreId));
    }

    public Autore saveAutore(AutoreDTO body) {
        if (this.autoriRepository.existsByAutore(body.autore())) throw new BadRequestException("Autore esistente");

        Autore nuovoAutore = new Autore(body.autore());
        this.autoriRepository.save(nuovoAutore);
        System.out.println("Autore salvato");
        return nuovoAutore;
    }

    public void deleteAutore(DeleteAutoreDTO body) {
        Autore autore = this.findAutoreById(body.autoreId());

        this.autoriRepository.delete(autore);

        System.out.println("Autore eliminato");
    }
}
