package giada.tonni.biblioteca.runners;

import giada.tonni.biblioteca.entities.Ruolo;
import giada.tonni.biblioteca.payloads.RuoloDTO;
import giada.tonni.biblioteca.services.RuoliService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StartRunner implements CommandLineRunner {

    private final RuoliService ruoliService;

    public StartRunner(RuoliService ruoliService) {
        this.ruoliService = ruoliService;
    }

    @Override
    public void run(String... args) throws Exception {

        List<Ruolo> listaRuoli = this.ruoliService.findAllRuoli();
        if (listaRuoli.isEmpty()) {
            RuoloDTO admin = new RuoloDTO("ADMIN");
            RuoloDTO utente = new RuoloDTO("UTENTE");

            this.ruoliService.saveRuolo(admin);
            this.ruoliService.saveRuolo(utente);
        }

    }
}
