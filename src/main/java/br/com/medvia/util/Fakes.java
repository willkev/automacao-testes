package br.com.medvia.util;

import br.com.medvia.db.WkDB;
import br.com.medvia.resources.Equipment;
import br.com.medvia.resources.Institution;
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

    public static List<Institution> createInstitutions() {
        List<Institution> list = new ArrayList<>();

        Institution i1 = new Institution();
        i1.setDescription("Menino Deus");
        list.add(i1);

        Institution i2 = new Institution();
        i2.setDescription("Hospital de Clínicas (POA)");
        list.add(i2);

        return list;
    }

    public static List<Equipment> createEquipments() {
        WkDB<Institution> dbInstitution = new WkDB<>(Institution.class);
        dbInstitution.dropAndCreateTable();
        List<Institution> institutions = createInstitutions();
        for (Institution institution : institutions) {
            dbInstitution.insert(institution);
        }

        List<Equipment> list = new ArrayList<>();

        Equipment equip1 = new Equipment();
        equip1.setDescription("Tomografia 24 Horas");
        equip1.setInstitutionID(1);
        equip1.setBrand("Siemens");
        equip1.setManufacturer("Siemens");
        equip1.setSerieNumber("EGH-90876-UKMG");
        list.add(equip1);

        Equipment equip2 = new Equipment();
        equip2.setDescription("Ressonancia 20 Tesla");
        equip2.setInstitutionID(1);
        equip2.setBrand("Philips");
        equip2.setManufacturer("Philips");
        equip2.setSerieNumber("823928276-UKMG");
        list.add(equip2);

        Equipment equip3 = new Equipment();
        equip3.setDescription("Cama levantadora");
        equip3.setInstitutionID(1);
        equip3.setBrand("Siemens");
        equip3.setManufacturer("Siemens");
        equip3.setSerieNumber("OLPK-490196-UKMG");
        list.add(equip3);

        Equipment equip4 = new Equipment();
        equip4.setDescription("Arco de luz LED");
        equip4.setInstitutionID(2);
        equip4.setBrand("Samsung");
        equip4.setManufacturer("Samsung");
        equip4.setSerieNumber("45590-AJQOA-39404");
        list.add(equip4);

        Equipment equip5 = new Equipment();
        equip5.setDescription("Furador de crânio");
        equip5.setInstitutionID(2);
        equip5.setBrand("LG");
        equip5.setManufacturer("LG");
        equip5.setSerieNumber("906627119909");
        list.add(equip5);

        Equipment equip6 = new Equipment();
        equip6.setDescription("Nobreak 900mA");
        equip6.setInstitutionID(2);
        equip6.setBrand("Brastemp");
        equip6.setManufacturer("Brastemp");
        equip6.setSerieNumber("0918204-49");
        list.add(equip6);

        return list;
    }

    public static List<User> createUsers() {
        List<User> list = new ArrayList<>();

        User user1 = new User();
        user1.setName("Dr. do Hospital");
        list.add(user1);

        User user2 = new User();
        user2.setName("Dr. Fulano Bento");
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
        List<Ticket> list = new ArrayList<>();

        Ticket t1 = new Ticket();
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
        t1.setState("a");
        t1.setPriority("a");
        list.add(t1);

        Ticket t2 = new Ticket();
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
        t2.setState("a");
        t2.setPriority("a");
        list.add(t2);

        Ticket t3 = new Ticket();
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
        t3.setState("a");
        t3.setPriority("n");
        list.add(t3);

        Ticket t4 = new Ticket();
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
        t4.setState("f");
        t4.setPriority("b");
        list.add(t4);

        Ticket t5 = new Ticket();
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
        t5.setState("e");
        t5.setPriority("b");
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
        n1.setUserID(users.get(random.nextInt(users.size())).getId());
        notes.add(n1);

        Note n2 = new Note();
        n2.setDescription("Pendência com o técnico, o mesmo retorna segunda-feira");
        n2.setDate(dateFormater.format(new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 300)))); // -300hr
        n2.setTickteID(ticketID);
        n2.setUserID(users.get(random.nextInt(users.size())).getId());
        notes.add(n2);

        Note n3 = new Note();
        n3.setDescription("Alterado o estado do chamado, pois é urgente!!!");
        n3.setDate(dateFormater.format(new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 50)))); // -50hr
        n3.setTickteID(ticketID);
        n3.setUserID(users.get(random.nextInt(users.size())).getId());
        notes.add(n3);

        return notes;
    }
}
