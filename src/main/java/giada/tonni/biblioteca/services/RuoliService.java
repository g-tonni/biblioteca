package giada.tonni.biblioteca.services;

import giada.tonni.biblioteca.entities.Ruolo;
import giada.tonni.biblioteca.exceptions.BadRequestException;
import giada.tonni.biblioteca.payloads.RuoloDTO;
import giada.tonni.biblioteca.repositories.RuoliRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuoliService {

    private final RuoliRepository ruoliRepository;

    public RuoliService(RuoliRepository ruoliRepository) {
        this.ruoliRepository = ruoliRepository;
    }

    public Ruolo saveRuolo(RuoloDTO body) {

        if (this.ruoliRepository.existsById(body.ruolo())) throw new BadRequestException("Ruolo già esistente");

        Ruolo nuovoRuolo = new Ruolo(body.ruolo());
        this.ruoliRepository.save(nuovoRuolo);
        System.out.println("Ruolo salvato");
        return nuovoRuolo;
    }

    public List<Ruolo> findAllRuoli() {
        return this.ruoliRepository.findAll();
    }
}
