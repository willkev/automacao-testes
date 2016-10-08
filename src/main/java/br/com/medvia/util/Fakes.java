package br.com.medvia.util;

import br.com.medvia.resources.Cost;
import br.com.medvia.resources.Equipment;
import br.com.medvia.resources.Institution;
import br.com.medvia.resources.Note;
import br.com.medvia.resources.NoteQualityControl;
import br.com.medvia.resources.QualityControl;
import br.com.medvia.resources.Ticket;
import br.com.medvia.resources.TypeEquipment;
import br.com.medvia.resources.User;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Willian Kirschner willkev@gmail.com
 */
public class Fakes {

    public static final SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public static List<Institution> createInstitutions() {
        List<Institution> list = new ArrayList<>();

        Institution i1 = new Institution();
        i1.setDescription("Menino Deus");
        list.add(i1);

        Institution i2 = new Institution();
        i2.setDescription("Hospital de Clínicas (POA)");
        list.add(i2);

        Institution i3 = new Institution();
        i3.setDescription("Moinhos de Vento");
        list.add(i3);

        return list;
    }

    public static List<TypeEquipment> createTypesEquipment() {
        List<TypeEquipment> list = new ArrayList<>();

        list.add(new TypeEquipment("ACESSÓRIOS"));
        list.add(new TypeEquipment("ANALISADOR PORTÁTIL DE LIGAS"));
        list.add(new TypeEquipment("APARELHO DE ANESTESIA"));
        list.add(new TypeEquipment("CÂMARA DE IONIZAÇÃO"));
        list.add(new TypeEquipment("CÍCLOTRON"));
        list.add(new TypeEquipment("CINEFLUOROSCOPIA"));
        list.add(new TypeEquipment("COMPUTADOR PESSOAL"));
        list.add(new TypeEquipment("CONTADOR DE DOSES-CURIÔMETRO"));
        list.add(new TypeEquipment("CR - RADIOLOGIA COMPUTADORIZAD"));
        list.add(new TypeEquipment("CROMATÓGRAFO COM ECD"));
        list.add(new TypeEquipment("DENSITOMETRIA ÓSSEA"));
        list.add(new TypeEquipment("DETECTOR DE SUPERFÍCIE"));
        list.add(new TypeEquipment("DETECTOR GEIGER-MULLER"));
        list.add(new TypeEquipment("DIFRATÔMETRO DE RAIOS-X"));
        list.add(new TypeEquipment("DISPOSITIVOS PARA CQ"));
        list.add(new TypeEquipment("ECOGRAFIA"));
        list.add(new TypeEquipment("EQUIP. DE MEDIDA / SIMULADORES"));
        list.add(new TypeEquipment("ESPECTRÔMETRO DE RAIOS X"));
        list.add(new TypeEquipment("ESTAÇÃO DE TRABALHO/INTERPRET."));
        list.add(new TypeEquipment("FONTES RADIOATIVAS"));
        list.add(new TypeEquipment("HOLTER"));
        list.add(new TypeEquipment("IDENTIFICADORA DE FILMES"));
        list.add(new TypeEquipment("IMPRESSORA"));
        list.add(new TypeEquipment("INJETORA DE CONTRASTE"));
        list.add(new TypeEquipment("INJETORA DE MEDICAMENTO RM"));
        list.add(new TypeEquipment("LITOTRIPSIA EXTRACORPÓREA"));
        list.add(new TypeEquipment("MAMOGRAFIA ANALÓGICA"));
        list.add(new TypeEquipment("MAMOGRAFIA DIGITAL"));
        list.add(new TypeEquipment("MEDICINA NUCLEAR - GAMA CÂMARA"));
        list.add(new TypeEquipment("MEDIDOR DE NÍVEL"));
        list.add(new TypeEquipment("MEDIDOR DE PARTÍCULAS EM SUSP."));
        list.add(new TypeEquipment("MEDIDOR NUCLEAR"));
        list.add(new TypeEquipment("MICROSCÓPIO ELET. DE VARREDURA"));
        list.add(new TypeEquipment("MISTURADOR DE QUÍMICOS"));
        list.add(new TypeEquipment("MONITOR DE COMPUTADOR"));
        list.add(new TypeEquipment("PET-CT"));
        list.add(new TypeEquipment("PROCESSADORA SECA - DRY"));
        list.add(new TypeEquipment("PROCESSADORA ÚMIDA"));
        list.add(new TypeEquipment("RADIOIMUNOENSAIO"));
        list.add(new TypeEquipment("RADIOTERAPIA"));
        list.add(new TypeEquipment("RAIO-X CONVENCIONAL"));
        list.add(new TypeEquipment("RAIOS X C/ SERIÓGRAFO"));
        list.add(new TypeEquipment("RAIOS X INDUSTRIAL"));
        list.add(new TypeEquipment("RAIOS X MÓVEL"));
        list.add(new TypeEquipment("RAIOS X PANORÂMICO"));
        list.add(new TypeEquipment("RAIOS X PERIAPICAL"));
        list.add(new TypeEquipment("RAIOS X TELECOMANDADO"));
        list.add(new TypeEquipment("RAIOS X TRANSPORTÁVEL ARCO-C"));
        list.add(new TypeEquipment("RAIOS-X DE BAGAGENS"));
        list.add(new TypeEquipment("RAIOS-X EDUCACIONAL"));
        list.add(new TypeEquipment("RESSONÂNCIA MAGNÉTICA"));
        list.add(new TypeEquipment("SISTEMA DE TRAT. EFLUENTES"));
        list.add(new TypeEquipment("SOFTWARE"));
        list.add(new TypeEquipment("STEREOTAXIA"));
        list.add(new TypeEquipment("TOMOGRAFIA COMPUTADORIZADA"));

        return list;
    }

    public static List<Equipment> createEquipments(List<Institution> institutions, List<TypeEquipment> typesEquipment) {
        List<Equipment> list = new ArrayList<>();

        Equipment equip1 = new Equipment();
        equip1.setName("Tomografia 24 Horas");
        equip1.setInstitutionId(institutions.get(0).getId());
        equip1.setBrand("Siemens");
        equip1.setManufacturer("Siemens");
        equip1.setSerieNumber("EGH-90876-UKMG");
        equip1.setTypeEquipmentId(typesEquipment.get(0).getId());
        list.add(equip1);

        Equipment equip2 = new Equipment();
        equip2.setName("Ressonancia 20 Tesla");
        equip2.setInstitutionId(institutions.get(0).getId());
        equip2.setBrand("Philips");
        equip2.setManufacturer("Philips");
        equip2.setSerieNumber("823928276-UKMG");
        equip2.setTypeEquipmentId(typesEquipment.get(10).getId());
        list.add(equip2);

        Equipment equip3 = new Equipment();
        equip3.setName("Cama levantadora");
        equip3.setInstitutionId(institutions.get(1).getId());
        equip3.setBrand("Siemens");
        equip3.setManufacturer("Siemens");
        equip3.setSerieNumber("OLPK-490196-UKMG");
        equip3.setTypeEquipmentId(typesEquipment.get(20).getId());
        list.add(equip3);

        Equipment equip4 = new Equipment();
        equip4.setName("Arco de luz LED");
        equip4.setInstitutionId(institutions.get(2).getId());
        equip4.setBrand("Samsung");
        equip4.setManufacturer("Samsung");
        equip4.setSerieNumber("45590-AJQOA-39404");
        equip4.setTypeEquipmentId(typesEquipment.get(30).getId());
        list.add(equip4);

        Equipment equip5 = new Equipment();
        equip5.setName("Furador de crânio");
        equip5.setInstitutionId(institutions.get(2).getId());
        equip5.setBrand("LG");
        equip5.setManufacturer("LG");
        equip5.setSerieNumber("906627119909");
        equip5.setTypeEquipmentId(typesEquipment.get(40).getId());
        list.add(equip5);

        Equipment equip6 = new Equipment();
        equip6.setName("Nobreak 900mA");
        equip6.setInstitutionId(institutions.get(2).getId());
        equip6.setBrand("Brastemp");
        equip6.setManufacturer("Brastemp");
        equip6.setSerieNumber("0918204-49");
        equip6.setTypeEquipmentId(typesEquipment.get(50).getId());
        list.add(equip6);

        return list;
    }

    public static List<User> createUsers() {
        List<User> list = new ArrayList<>();

        User user1 = new User();
        user1.setName("Dr. do Hospital");
        user1.setEmail("user1");
        user1.setPassword("1111");
        list.add(user1);

        User user2 = new User();
        user2.setName("Dr. Fulano Bento");
        user2.setEmail("user2");
        user2.setPassword("2222");
        list.add(user2);

        User user3 = new User();
        user3.setName("Dr. Flemming");
        user3.setEmail("user3");
        user3.setPassword("3333");
        list.add(user3);

        User user4 = new User();
        user4.setName("Técnico Radiologista");
        user4.setEmail("user4");
        user4.setPassword("4444");
        list.add(user4);

        User user5 = new User();
        user5.setName("Mario Luigui da Silva");
        user5.setEmail("user5");
        user5.setPassword("5555");
        list.add(user5);

        User user6 = new User();
        user6.setName("Doctor Who");
        user6.setEmail("user6");
        user6.setPassword("6666");
        list.add(user6);

        return list;
    }

    public static List<Ticket> createTickets(List<User> users, List<Equipment> equipments) {
        List<Ticket> list = new ArrayList<>();

        Ticket t1 = new Ticket();
        t1.setTitle("Fonte queimada");
        Date date = new Date();
        t1.setDateOcurrence(dateFormater.format(date));
        date = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 13)); // -13hr
        t1.setPrediction(dateFormater.format(date));
        t1.setDescription("Fonte trifásica queimada por raio no dia 12/07");
        t1.setEquipmentId(equipments.get(0).getId());
        t1.setResponsableId(users.get(0).getId());
        t1.setUserId(users.get(1).getId());
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
        t2.setEquipmentId(equipments.get(1).getId());
        t2.setResponsableId(users.get(2).getId());
        t2.setUserId(users.get(3).getId());
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
        t3.setEquipmentId(equipments.get(2).getId());
        t3.setResponsableId(users.get(4).getId());
        t3.setUserId(users.get(5).getId());
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
        t4.setEquipmentId(equipments.get(3).getId());
        t4.setResponsableId(users.get(1).getId());
        t4.setUserId(users.get(5).getId());
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
        t5.setEquipmentId(equipments.get(4).getId());
        t5.setResponsableId(users.get(2).getId());
        t5.setUserId(users.get(4).getId());
        t5.setSituation("100");
        t5.setState("e");
        t5.setPriority("b");
        list.add(t5);

        return list;
    }

    public static List<Cost> createCosts(int ticketId, List<User> users) {
        List<Cost> costs = new ArrayList<>();
        // vai pegar randomicamente um user dentro dos disponíveis
        Random random = new Random(System.currentTimeMillis());

        Cost c1 = new Cost();
        c1.setValue(120.77);
        c1.setDescription("2 horas do técnico para instalar o equipamento");
        c1.setDate(dateFormater.format(new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 111)))); // -111hr
        c1.setTickteId(ticketId);
        c1.setUserId(users.get(random.nextInt(users.size())).getId());
        costs.add(c1);

        Cost c2 = new Cost();
        c2.setValue(240.00);
        c2.setDescription("Necessitou chamar um técnico na madrugada do final de semana, por isso custou mais caro");
        c2.setDate(dateFormater.format(new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 11)))); // -11hr
        c2.setTickteId(ticketId);
        c2.setUserId(users.get(random.nextInt(users.size())).getId());
        costs.add(c2);

        Cost c3 = new Cost();
        c3.setValue(50.00);
        c3.setDescription("50 pila de cabos blindados");
        c3.setDate(dateFormater.format(new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 23)))); // -23hr
        c3.setTickteId(ticketId);
        c3.setUserId(users.get(random.nextInt(users.size())).getId());
        costs.add(c3);

        return costs;
    }

    public static List<Note> createNotes(int ticketId, List<User> users) {
        List<Note> notes = new ArrayList<>();
        // vai pegar randomicamente um user dentro dos disponíveis
        Random random = new Random(System.currentTimeMillis());

        Note n1 = new Note();
        n1.setDescription("Este chamado está demorando muito, pois os responsáveis não foram avisados no dia do ocorrido");
        n1.setDate(dateFormater.format(new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 150)))); // -150hr
        n1.setTickteId(ticketId);
        n1.setUserId(users.get(random.nextInt(users.size())).getId());
        notes.add(n1);

        Note n2 = new Note();
        n2.setDescription("Pendência com o técnico, o mesmo retorna segunda-feira");
        n2.setDate(dateFormater.format(new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 300)))); // -300hr
        n2.setTickteId(ticketId);
        n2.setUserId(users.get(random.nextInt(users.size())).getId());
        notes.add(n2);

        Note n3 = new Note();
        n3.setDescription("Alterado o estado do chamado, pois é urgente!!!");
        n3.setDate(dateFormater.format(new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 50)))); // -50hr
        n3.setTickteId(ticketId);
        n3.setUserId(users.get(random.nextInt(users.size())).getId());
        notes.add(n3);

        return notes;
    }

    public static List<QualityControl> createQualityControl(List<User> users, List<Equipment> equipments) {
        List<QualityControl> list = new ArrayList<>();

        QualityControl q1 = new QualityControl();
        q1.setTest("Máquina validada OK");
        q1.setCompliance(true);
        q1.setEquipmentId(equipments.get(0).getId());
        q1.setUserId(users.get(0).getId());
        q1.setDateExecution(dateFormater.format(new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24 * 90)))); //-90 dias
        q1.setDateValidity(dateFormater.format(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 90))));  //+90 dias
        list.add(q1);

        QualityControl q2 = new QualityControl();
        q2.setTest("Teste de estress falhou");
        q2.setCompliance(false);
        q2.setEquipmentId(equipments.get(1).getId());
        q2.setUserId(users.get(1).getId());
        q2.setDateExecution(dateFormater.format(new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24 * 10)))); //-10 dias
        q2.setDateValidity(dateFormater.format(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 10)))); //+10 dias
        list.add(q2);

        QualityControl q3 = new QualityControl();
        q3.setTest("Teste falhou novamente!!!");
        q3.setCompliance(false);
        q3.setEquipmentId(equipments.get(2).getId());
        q3.setUserId(users.get(2).getId());
        q3.setDateExecution(dateFormater.format(new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24 * 5)))); //-5 dias
        q3.setDateValidity(dateFormater.format(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 3)))); // +3 dias
        list.add(q3);

        QualityControl q4 = new QualityControl();
        q4.setTest("Validação OK 100%");
        q4.setCompliance(true);
        q4.setEquipmentId(equipments.get(3).getId());
        q4.setUserId(users.get(3).getId());
        q4.setDateExecution(dateFormater.format(new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24 * 50)))); //-50 dias
        q4.setDateValidity(dateFormater.format(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 200)))); // +200 dias
        list.add(q4);
        return list;
    }

    public static List<NoteQualityControl> createNotesQualityControl(int idQC, List<User> users) {
        List<NoteQualityControl> notes = new ArrayList<>();
        // vai pegar randomicamente um user dentro dos disponíveis
        Random random = new Random(System.currentTimeMillis());

        NoteQualityControl n1 = new NoteQualityControl();
        n1.setDescription("Não foi boa a avaliação do técnico sobre o teste do aparelho!");
        n1.setDate(dateFormater.format(new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 150)))); // -150hr
        n1.setQualityControlId(idQC);
        n1.setUserId(users.get(random.nextInt(users.size())).getId());
        notes.add(n1);

        NoteQualityControl n2 = new NoteQualityControl();
        n2.setDescription("Pendência com o técnico, o mesmo retorna segunda-feira para refazer o teste.");
        n2.setDate(dateFormater.format(new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 300)))); // -300hr
        n2.setQualityControlId(idQC);
        n2.setUserId(users.get(random.nextInt(users.size())).getId());
        notes.add(n2);

        NoteQualityControl n3 = new NoteQualityControl();
        n3.setDescription("Alterado o estado do chamado, pois é urgente que se faça novo teste!!!");
        n3.setDate(dateFormater.format(new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 50)))); // -50hr
        n3.setQualityControlId(idQC);
        n3.setUserId(users.get(random.nextInt(users.size())).getId());
        notes.add(n3);

        return notes;
    }

}
