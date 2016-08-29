package br.com.medvia.util;

import br.com.medvia.resources.Ticket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Willian
 */
public class FakesTickets {

    public static List<Ticket> createFakes(int repeat) {
        SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Ticket t1 = new Ticket();
        t1.setID(1357);
        t1.setDateOcurrenceRaw(new Date());
        t1.setDateOcurrence(dateFormater.format(t1.getDateOcurrenceRaw()));
        t1.setPredictionRaw(new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 13))); // -13hr
        t1.setPrediction(dateFormater.format(t1.getPredictionRaw()));
        t1.setDescription("Fonte queimada");
        t1.setEquipment("Tomografia 27H");
        t1.setInstitution("Hospital Moinhos de Vento - Sede Ramiro");
        t1.setResponsable("Dr. Fulano Bento");
        t1.setOpenedBy("Dr. do Hospital");
        t1.setSituation(75);
        t1.setState("f");
        t1.setPriority("a");

        Ticket t2 = new Ticket();
        t2.setID(777);
        t2.setDateOcurrenceRaw(new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24))); // -24hr
        t2.setDateOcurrence(dateFormater.format(t2.getDateOcurrenceRaw()));
        t2.setPredictionRaw(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 200))); // +200hr
        t2.setPrediction(dateFormater.format(t2.getPredictionRaw()));
        t2.setDescription("Quebrou luz de fundo");
        t2.setEquipment("Ressonancia");
        t2.setInstitution("Hospital Tramadaí - RS");
        t2.setResponsable("Dr. Flemming");
        t2.setOpenedBy("Mario Junior");
        t2.setSituation(100);
        t2.setState("e");
        t2.setPriority("b");

        Ticket t3 = new Ticket();
        t3.setID(222);
        t3.setDateOcurrenceRaw(new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 10))); // -10hr
        t3.setDateOcurrence(dateFormater.format(t3.getDateOcurrenceRaw()));
        t3.setPredictionRaw(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 63))); // +63hr
        t3.setPrediction(dateFormater.format(t3.getPredictionRaw()));
        t3.setDescription("Motor de subida sem força");
        t3.setEquipment("Cama levantadora");
        t3.setInstitution("Hospital Moinhos de Vento - Sede Ramiro");
        t3.setResponsable("Dr. Arno K.");
        t3.setOpenedBy("Técnico Abreu Lima");
        t3.setSituation(50);
        t3.setState("a");
        t3.setPriority("n");

        Ticket t4 = new Ticket();
        t4.setID(13);
        t4.setDateOcurrenceRaw(new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 8))); // -8hr
        t4.setDateOcurrence(dateFormater.format(t4.getDateOcurrenceRaw()));
        t4.setPredictionRaw(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 100))); // +100hr
        t4.setPrediction(dateFormater.format(t4.getPredictionRaw()));
        t4.setDescription("3 grupos de LEDs queimaram");
        t4.setEquipment("Arco de luz LED");
        t4.setInstitution("Hospital Julio Cesar");
        t4.setResponsable("Dr. Freud");
        t4.setOpenedBy("Dr. Radiologista");
        t4.setSituation(50);
        t4.setState("a");
        t4.setPriority("a");
        if (repeat < 1) {
            repeat = 1;
        }
        List<Ticket> fakes = new ArrayList<Ticket>();
        for (int i = 0; i < repeat; i++) {
            for (Ticket t : fakes) {
                fakes.add(t);
            }
        }
        return fakes;
    }
}
