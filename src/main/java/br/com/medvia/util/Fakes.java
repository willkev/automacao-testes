package br.com.medvia.util;

import br.com.medvia.resources.Equipment;
import br.com.medvia.resources.Note;
import br.com.medvia.resources.Ticket;
import br.com.medvia.resources.User;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Willian
 */
public class Fakes {

    public static List<Equipment> createEquipments() {
        List<Equipment> list = new ArrayList<Equipment>();

        Equipment equip1 = new Equipment();
        equip1.setDescription("Tomografia 24 Horas");
        list.add(equip1);

        Equipment equip2 = new Equipment();
        equip2.setDescription("Ressonancia 20 Tesla");
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
        user5.setName("Mario Luigui da Silva");
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
        t1.setTitle("Fonte queimada");
        Date date = new Date();
        t1.setDateOcurrence(dateFormater.format(date));
        date = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 13)); // -13hr
        t1.setPrediction(dateFormater.format(date));
        t1.setDescription("Fonte trifásica queimada por raio no dia 12/07");
        t1.setEquipmentID(equipments.get(0).getId());
        t1.setResponsableID(users.get(0).getId());
        t1.setOpenedByID(users.get(1).getId());
        t1.setSituation("0");
        t1.setState("1a");
        t1.setPriority("1a");
        list.add(t1);

        Ticket t2 = new Ticket();
        t2.setId(777);
        t2.setTitle("Arrumar luz urgente");
        date = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24)); // -24hr
        t2.setDateOcurrence(dateFormater.format(date));
        date = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 200)); // +200hr
        t2.setPrediction(dateFormater.format(date));
        t2.setDescription("Quebrou a luz de fundo do aparelho teletransportados de bosons de higges");
        t2.setEquipmentID(equipments.get(1).getId());
        t2.setResponsableID(users.get(2).getId());
        t2.setOpenedByID(users.get(3).getId());
        t2.setSituation("50");
        t2.setState("1a");
        t2.setPriority("1a");
        list.add(t2);

        Ticket t3 = new Ticket();
        t3.setId(222);
        t3.setTitle("Chamado para mecânico");
        date = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 10)); // -10hr
        t3.setDateOcurrence(dateFormater.format(date));
        date = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 63)); // +63hr
        t3.setPrediction(dateFormater.format(date));
        t3.setDescription("Motor de subida da cama para ressonancia está sem força quando paciente tem mais de 700kg");
        t3.setEquipmentID(equipments.get(2).getId());
        t3.setResponsableID(users.get(4).getId());
        t3.setOpenedByID(users.get(5).getId());
        t3.setSituation("0");
        t3.setState("1a");
        t3.setPriority("2n");
        list.add(t3);

        Ticket t4 = new Ticket();
        t4.setId(13);
        t4.setTitle("Quarto chamado!");
        date = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 8)); // -8hr
        t4.setDateOcurrence(dateFormater.format(date));
        date = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 100)); // +100hr
        t4.setPrediction(dateFormater.format(date));
        t4.setDescription("3 grupos de LEDs queimaram na máquina de café");
        t4.setEquipmentID(equipments.get(3).getId());
        t4.setResponsableID(users.get(1).getId());
        t4.setOpenedByID(users.get(5).getId());
        t4.setSituation("100");
        t4.setState("2f");
        t4.setPriority("3b");
        list.add(t4);

        Ticket t5 = new Ticket();
        t5.setId(13);
        t5.setTitle("Quarto chamado!");
        date = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 80)); // -80hr
        t5.setDateOcurrence(dateFormater.format(date));
        date = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 200)); // +200hr
        t5.setPrediction(dateFormater.format(date));
        t5.setDescription("3 grupos de LEDs queimaram na máquina de café");
        t5.setEquipmentID(equipments.get(4).getId());
        t5.setResponsableID(users.get(2).getId());
        t5.setOpenedByID(users.get(4).getId());
        t5.setSituation("100");
        t5.setState("3e");
        t5.setPriority("3b");
        list.add(t5);

        return list;
    }

    public static List<Note> createNotes(int ticketID, List<User> users) {
        SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        List<Note> notes = new ArrayList<>();
        // vai pegar randomicamente um user dentro dos disponíveis
        Random random = new Random(System.currentTimeMillis());

        Note n1 = new Note();
        n1.setDescription("Este chamado está demorando muito, pois os responsáveis não foram avisados no dia do ocorrido");
        n1.setDate(dateFormater.format(new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 150)))); // -150hr
        n1.setTickteID(ticketID);
        n1.setUserID(users.get(random.nextInt(notes.size())).getId());
        notes.add(n1);

        Note n2 = new Note();
        n2.setDescription("Pendência com o técnico, o mesmo retorna segunda-feira");
        n2.setDate(dateFormater.format(new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 300)))); // -300hr
        n2.setTickteID(ticketID);
        n2.setUserID(users.get(random.nextInt(notes.size())).getId());
        notes.add(n2);

        Note n3 = new Note();
        n3.setDescription("Alterado o estado do chamado, pois é urgente!!!");
        n3.setDate(dateFormater.format(new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 50)))); // -50hr
        n3.setTickteID(ticketID);
        n3.setUserID(users.get(random.nextInt(notes.size())).getId());
        notes.add(n3);

        return notes;
    }
}
