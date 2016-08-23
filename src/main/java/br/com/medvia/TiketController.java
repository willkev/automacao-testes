package br.com.medvia;

import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Willian
 */
@RestController
public class TiketController {

    @CrossOrigin
    @RequestMapping("/api/tickets/list")
    public String getAllTickets(@RequestParam(value = "repeat", defaultValue = "1") int repeat) {
        System.out.println("repeat=" + repeat);
        SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Gson gson = new Gson();

        Ticket t1 = new Ticket();
        t1.setId(1357);
        t1.setDateOcurrenceRaw(new Date());
        t1.setDateOcurrence(dateFormater.format(t1.getDateOcurrenceRaw()));
        t1.setDescription("Fonte queimada");
        t1.setEquipment("Tomografia 27H");
        t1.setInstitution("Hospital Moinhos de Vento - Sede Ramiro");
        t1.setResponsable("Dr. Fulano Bento");
        t1.setSituation(75);
        t1.setState(false);

        Ticket t2 = new Ticket();
        t2.setId(777);
        t2.setDateOcurrenceRaw(new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24))); // -24hr
        t2.setDateOcurrence(dateFormater.format(t2.getDateOcurrenceRaw()));
        t2.setDescription("Quebrou luz de fundo");
        t2.setEquipment("Ressonancia");
        t2.setInstitution("Hospital Tramadaí - RS");
        t2.setResponsable("Dr. Flemming");
        t2.setSituation(99);
        t2.setState(true);

        Ticket t3 = new Ticket();
        t3.setId(222);
        t3.setDateOcurrenceRaw(new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 10))); // -10hr
        t3.setDateOcurrence(dateFormater.format(t3.getDateOcurrenceRaw()));
        t3.setDescription("Motor de subida sem força");
        t3.setEquipment("Cama levantadora");
        t3.setInstitution("Hospital Moinhos de Vento - Sede Ramiro");
        t3.setResponsable("Dr. Arno K.");
        t3.setSituation(5);
        t3.setState(true);

        Ticket t4 = new Ticket();
        t4.setId(13);
        t4.setDateOcurrenceRaw(new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 8))); // -8hr
        t4.setDateOcurrence(dateFormater.format(t4.getDateOcurrenceRaw()));
        t4.setDescription("3 grupos de LEDs queimaram");
        t4.setEquipment("Arco de luz LED");
        t4.setInstitution("Hospital Julio Cesar");
        t4.setResponsable("Dr. Freud");
        t4.setSituation(50);
        t4.setState(false);

        if (repeat < 1) {
            repeat = 1;
        }
        Ticket[] pool = new Ticket[4 * repeat];
        for (int i = 0; i < pool.length; i += 4) {
            pool[i] = t1;
            pool[i + 1] = t2;
            pool[i + 2] = t3;
            pool[i + 3] = t4;
        }
        return gson.toJson(pool);
    }

}
