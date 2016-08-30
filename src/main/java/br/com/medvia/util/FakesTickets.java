package br.com.medvia.util;

import br.com.medvia.db.DBManager;
import br.com.medvia.db.WkDB;
import br.com.medvia.resources.Equipment;
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

    public static int createFakes(int repeat, WkDB<Ticket> dbTicket, WkDB<User> dbUser, WkDB<Equipment> dbEquipment) {
        User user1 = new User();
        user1.setName("Dr. Fulano Bento");
        dbUser.insert(user1);

        User user2 = new User();
        user2.setName("Dr. do Hospital");
        dbUser.insert(user2);

        User user3 = new User();
        user3.setName("Dr. Flemming");
        dbUser.insert(user3);

        User user4 = new User();
        user4.setName("Técnico Radiologista");
        dbUser.insert(user4);

        Equipment equip1 = new Equipment();
        equip1.setDescription("Tomografia 27H");
        dbEquipment.insert(equip1);

        Equipment equip2 = new Equipment();
        equip2.setDescription("Ressonancia");
        dbEquipment.insert(equip2);

        Equipment equip3 = new Equipment();
        equip3.setDescription("Cama levantadora");
        dbEquipment.insert(equip3);

        Equipment equip4 = new Equipment();
        equip4.setDescription("Arco de luz LED");
        dbEquipment.insert(equip4);

        SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Ticket t1 = new Ticket();
        t1.setID(1357);
        t1.setTitle("Chamado #1");
        Date date = new Date();
        t1.setDateOcurrence(dateFormater.format(date));
        date = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 13)); // -13hr
        t1.setPrediction(dateFormater.format(date));
        t1.setDescription("Fonte queimada");
        t1.setEquipmentID(equip1.getID());
//        t1.setInstitution("Hospital Moinhos de Vento - Sede Ramiro");
        t1.setResponsableID(user1.getID());
        t1.setOpenedByID(user1.getID());
        t1.setSituation(75);
        t1.setState("f");
        t1.setPriority("a");

        Ticket t2 = new Ticket();
        t2.setID(777);
        t2.setTitle("Segundo chamado");
        date = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24)); // -24hr
        t2.setDateOcurrence(dateFormater.format(date));
        date = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 200)); // +200hr
        t2.setPrediction(dateFormater.format(date));
        t2.setDescription("Quebrou luz de fundo");
        t2.setEquipmentID(equip2.getID());
//        t2.setInstitution("Hospital Tramadaí - RS");
        t2.setResponsableID(user2.getID());
        t2.setOpenedByID(user3.getID());
        t2.setSituation(100);
        t2.setState("e");
        t2.setPriority("b");

        Ticket t3 = new Ticket();
        t3.setID(222);
        t3.setTitle("3º chamado aberto");
        date = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 10)); // -10hr
        t3.setDateOcurrence(dateFormater.format(date));
        date = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 63)); // +63hr
        t3.setPrediction(dateFormater.format(date));
        t3.setDescription("Motor de subida sem força");
        t3.setEquipmentID(equip3.getID());
//        t3.setInstitution("Hospital Moinhos de Vento - Sede Ramiro");
        t3.setResponsableID(user3.getID());
        t3.setOpenedByID(user4.getID());
        t3.setSituation(0);
        t3.setState("a");
        t3.setPriority("n");

        Ticket t4 = new Ticket();
        t4.setID(13);
        t4.setTitle("Quarto chamado!!!");
        date = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 8)); // -8hr
        t4.setDateOcurrence(dateFormater.format(date));
        date = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 100)); // +100hr
        t4.setPrediction(dateFormater.format(date));
        t4.setDescription("3 grupos de LEDs queimaram");
        t4.setEquipmentID(equip4.getID());
//        t4.setInstitution("Hospital Julio Cesar");
        t4.setResponsableID(user4.getID());
        t4.setOpenedByID(user4.getID());
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
        System.out.println("Generated ticketsFakes.size() : " + fakes.size());
        for (Ticket ticketFake : fakes) {
            DBManager.getInstance().getDbTicket().insert(ticketFake);
        }
        return fakes.size();
    }
}
