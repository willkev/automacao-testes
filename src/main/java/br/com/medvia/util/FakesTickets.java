package br.com.medvia.util;

import br.com.medvia.resources.Ticket;
import br.com.medvia.resources.User;
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
        User user1 = new User();
        user1.setID(1);
        user1.setName("Dr. Fulano Bento");

        User user2 = new User();
        user2.setID(1);
        user2.setName("Dr. do Hospital");

        User user3 = new User();
        user3.setID(1);
        user3.setName("Dr. Flemming");

        User user4 = new User();
        user4.setID(1);
        user4.setName("Técnico Radiologista");

        SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Ticket t1 = new Ticket();
        t1.setID(1357);
        Date date = new Date();
        t1.setDateOcurrence(dateFormater.format(date));
        date = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 13)); // -13hr
        t1.setPrediction(dateFormater.format(date));
        t1.setDescription("Fonte queimada");
        t1.setEquipment("Tomografia 27H");
        t1.setInstitution("Hospital Moinhos de Vento - Sede Ramiro");
        t1.setResponsable(user1.getID());
        t1.setOpenedBy(user1.getID());
        t1.setSituation(75);
        t1.setState("f");
        t1.setPriority("a");

        Ticket t2 = new Ticket();
        t2.setID(777);
        date = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24)); // -24hr
        t2.setDateOcurrence(dateFormater.format(date));
        date = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 200)); // +200hr
        t2.setPrediction(dateFormater.format(date));
        t2.setDescription("Quebrou luz de fundo");
        t2.setEquipment("Ressonancia");
        t2.setInstitution("Hospital Tramadaí - RS");
        t2.setResponsable(user2.getID());
        t2.setOpenedBy(user3.getID());
        t2.setSituation(100);
        t2.setState("e");
        t2.setPriority("b");

        Ticket t3 = new Ticket();
        t3.setID(222);
        date = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 10)); // -10hr
        t3.setDateOcurrence(dateFormater.format(date));
        date = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 63)); // +63hr
        t3.setPrediction(dateFormater.format(date));
        t3.setDescription("Motor de subida sem força");
        t3.setEquipment("Cama levantadora");
        t3.setInstitution("Hospital Moinhos de Vento - Sede Ramiro");
        t3.setResponsable(user3.getID());
        t3.setOpenedBy(user4.getID());
        t3.setSituation(0);
        t3.setState("a");
        t3.setPriority("n");

        Ticket t4 = new Ticket();
        t4.setID(13);
        date = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 8)); // -8hr
        t4.setDateOcurrence(dateFormater.format(date));
        date = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 100)); // +100hr
        t4.setPrediction(dateFormater.format(date));
        t4.setDescription("3 grupos de LEDs queimaram");
        t4.setEquipment("Arco de luz LED");
        t4.setInstitution("Hospital Julio Cesar");
        t4.setResponsable(user4.getID());
        t4.setOpenedBy(user4.getID());
        t4.setSituation(50);
        t4.setState("a");
        t4.setPriority("a");

        if (repeat < 1) {
            repeat = 1;
        }
        List<Ticket> fakes = new ArrayList<Ticket>();
        for (int i = 0; i < repeat; i++) {
            fakes.add(t1);
            fakes.add(t2);
            fakes.add(t3);
            fakes.add(t4);
        }
        return fakes;
    }
}
