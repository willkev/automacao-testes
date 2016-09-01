package br.com.medvia.util;

import br.com.medvia.resources.Equipment;
import br.com.medvia.resources.Note;
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
public class Fakes {

    public static List<Equipment> createEquipments() {
        List<Equipment> list = new ArrayList<Equipment>();

        Equipment equip1 = new Equipment();
        equip1.setDescription("Tomografia 27H");
        list.add(equip1);

        Equipment equip2 = new Equipment();
        equip2.setDescription("Ressonancia 17 Tesla");
        list.add(equip2);

        Equipment equip3 = new Equipment();
        equip3.setDescription("Cama levantadora");
        list.add(equip3);

        Equipment equip4 = new Equipment();
        equip4.setDescription("Arco de luz LED");
        list.add(equip4);

        Equipment equip5 = new Equipment();
        equip5.setDescription("Furador de crânio");
        list.add(equip5);

        Equipment equip6 = new Equipment();
        equip6.setDescription("Nobreak 900mA");
        list.add(equip6);

        return list;
    }

    public static List<User> createUsers() {
        List<User> list = new ArrayList<User>();

        User user1 = new User();
        user1.setName("Dr. Fulano Bento");
        list.add(user1);

        User user2 = new User();
        user2.setName("Dr. do Hospital");
        list.add(user2);

        User user3 = new User();
        user3.setName("Dr. Flemming");
        list.add(user3);

        User user4 = new User();
        user4.setName("Técnico Radiologista");
        list.add(user4);

        User user5 = new User();
        user5.setName("Mario pizzaiolo");
        list.add(user5);

        User user6 = new User();
        user6.setName("Doctor Who");
        list.add(user6);

        return list;
    }

    public static List<Ticket> createTickets(List<User> users, List<Equipment> equipments) {
        SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        List<Ticket> list = new ArrayList<Ticket>();

        Ticket t1 = new Ticket();
        t1.setId(1357);
        t1.setTitle("Chamado #1");
        Date date = new Date();
        t1.setDateOcurrence(dateFormater.format(date));
        date = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 13)); // -13hr
        t1.setPrediction(dateFormater.format(date));
        t1.setDescription("Fonte queimada");
        t1.setEquipmentID(equipments.get(0).getId());
        t1.setResponsableID(users.get(0).getId());
        t1.setOpenedByID(users.get(1).getId());
        t1.setSituation("75");
        t1.setState("f");
        t1.setPriority("a");
        list.add(t1);

        Ticket t2 = new Ticket();
        t2.setId(777);
        t2.setTitle("Segundo chamado");
        date = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24)); // -24hr
        t2.setDateOcurrence(dateFormater.format(date));
        date = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 200)); // +200hr
        t2.setPrediction(dateFormater.format(date));
        t2.setDescription("Quebrou luz de fundo");
        t2.setEquipmentID(equipments.get(1).getId());
        t2.setResponsableID(users.get(2).getId());
        t2.setOpenedByID(users.get(3).getId());
        t2.setSituation("100");
        t2.setState("e");
        t2.setPriority("b");
        list.add(t2);

        Ticket t3 = new Ticket();
        t3.setId(222);
        t3.setTitle("3º chamado aberto");
        date = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 10)); // -10hr
        t3.setDateOcurrence(dateFormater.format(date));
        date = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 63)); // +63hr
        t3.setPrediction(dateFormater.format(date));
        t3.setDescription("Motor de subida sem força");
        t3.setEquipmentID(equipments.get(2).getId());
        t3.setResponsableID(users.get(4).getId());
        t3.setOpenedByID(users.get(5).getId());
        t3.setSituation("0");
        t3.setState("a");
        t3.setPriority("n");
        list.add(t3);

        Ticket t4 = new Ticket();
        t4.setId(13);
        t4.setTitle("Quarto chamado!!!");
        date = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 8)); // -8hr
        t4.setDateOcurrence(dateFormater.format(date));
        date = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 100)); // +100hr
        t4.setPrediction(dateFormater.format(date));
        t4.setDescription("3 grupos de LEDs queimaram");
        t4.setEquipmentID(equipments.get(3).getId());
        t4.setResponsableID(users.get(2).getId());
        t4.setOpenedByID(users.get(4).getId());
        t4.setSituation("50");
        t4.setState("a");
        t4.setPriority("a");
        list.add(t4);

        return list;
    }

    public static List<Note> createNotes(List<User> users) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
