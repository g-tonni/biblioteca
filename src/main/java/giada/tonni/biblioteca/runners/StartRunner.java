package giada.tonni.biblioteca.runners;

import giada.tonni.biblioteca.entities.Ruolo;
import giada.tonni.biblioteca.entities.Utente;
import giada.tonni.biblioteca.payloads.RuoloDTO;
import giada.tonni.biblioteca.payloads.UtenteDTO;
import giada.tonni.biblioteca.services.RuoliService;
import giada.tonni.biblioteca.services.UtentiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class StartRunner implements CommandLineRunner {

    private final RuoliService ruoliService;
    private final UtentiService utentiService;
    private final String adminName;
    private final String adminSurname;
    private final LocalDate adminBirth;
    private final String adminEmail;
    private final String adminPassword;
    ;
    private final long adminPhone;
    ;

    public StartRunner(RuoliService ruoliService,
                       UtentiService utentiService,
                       @Value("${admin.name}") String adminName,
                       @Value("${admin.surname}") String adminSurname,
                       @Value("${admin.birth}") LocalDate adminBirth,
                       @Value("${admin.email}") String adminEmail,
                       @Value("${admin.password}") String adminPassword,
                       @Value("${admin.phone}") long adminPhone) {
        this.ruoliService = ruoliService;
        this.utentiService = utentiService;
        this.adminName = adminName;
        this.adminSurname = adminSurname;
        this.adminBirth = adminBirth;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
        this.adminPhone = adminPhone;
    }

    @Override
    public void run(String... args) throws Exception {

        List<Ruolo> listaRuoli = this.ruoliService.findAllRuoli();
        if (listaRuoli.isEmpty()) {
            RuoloDTO admin = new RuoloDTO("ROLE_ADMIN");
            RuoloDTO utente = new RuoloDTO("ROLE_UTENTE");

            this.ruoliService.saveRuolo(admin);
            this.ruoliService.saveRuolo(utente);
        }

        List<Utente> listaUtenti = this.utentiService.findAllUtenti();

        if (listaUtenti.isEmpty()) {
            UtenteDTO adminBody = new UtenteDTO(adminName, adminSurname, adminBirth, adminEmail, adminPassword, adminPhone);
            this.utentiService.addFirstAdmin(adminBody);
        }


    }


}
