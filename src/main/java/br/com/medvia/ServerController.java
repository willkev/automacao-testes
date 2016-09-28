package br.com.medvia;

import br.com.medvia.db.WkDB;
import br.com.medvia.resources.Cost;
import br.com.medvia.resources.Equipment;
import br.com.medvia.resources.Institution;
import br.com.medvia.resources.Note;
import br.com.medvia.resources.Ticket;
import br.com.medvia.resources.TypeEquipment;
import br.com.medvia.resources.User;
import br.com.medvia.util.Fakes;
import br.com.medvia.util.ReplyMessage;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {

    // java.io.tmpdir=C:\Users\willian.kirschner\AppData\Roaming\NetBeans\8.1\apache-tomcat-8.0.27.0_base\temp
    // user.home=C:\Users\willian.kirschner
    //
    // java.io.tmpdir=/var/cache/tomcat8/temp
    // user.home=/usr/share/tomcat8
    private final File fileDB;

    private final WkDB<Ticket> dbTicket;
    private final WkDB<Equipment> dbEquipment;
    private final WkDB<User> dbUser;
    private final WkDB<Note> dbNote;
    private final WkDB<Cost> dbCost;
    private final WkDB<Institution> dbInstitution;
    private final WkDB<TypeEquipment> dbTypeEquipment;

    public ServerController() {
        System.out.println(ServerController.class.getSimpleName() + " OK!");

        //String propJavaTmp = System.getProperty("java.io.tmpdir");
        String propUserHome = System.getProperty("user.home");
        fileDB = new File(propUserHome, "medvia.db");
        System.out.println("fileDB=" + fileDB.getAbsolutePath());

        WkDB.setFileDB(fileDB);

        dbTicket = new WkDB<>(Ticket.class);
        dbEquipment = new WkDB<>(Equipment.class);
        dbUser = new WkDB<>(User.class);
        dbNote = new WkDB<>(Note.class);
        dbCost = new WkDB<>(Cost.class);
        dbInstitution = new WkDB<>(Institution.class);
        dbTypeEquipment = new WkDB<>(TypeEquipment.class);
        // se o arquivo ainda não existir
        if (!fileDB.exists() || fileDB.length() < 1) {
            dbTicket.createTable();
            dbEquipment.createTable();
            dbUser.createTable();
            dbNote.createTable();
            dbCost.createTable();
            dbInstitution.createTable();
            dbTypeEquipment.createTable();
        }
    }

    @RequestMapping("/dbdrop")
    public ResponseEntity<String> dbReset() {
        dbTicket.dropAndCreateTable();
        dbEquipment.dropAndCreateTable();
        dbUser.dropAndCreateTable();
        dbNote.dropAndCreateTable();
        dbCost.dropAndCreateTable();
        dbInstitution.dropAndCreateTable();
        dbTypeEquipment.dropAndCreateTable();

        System.out.println("dropAndCreateTable!");
        return new ResponseEntity<>("DB droped OK!", HttpStatus.OK);
    }

    @RequestMapping("/dbcreate")
    public ResponseEntity<String> dbCreate() {
        dbReset();

        UserController u = new UserController();
        InstitutionController i = new InstitutionController();
        EquipmentController e = new EquipmentController();
        TicketController t = new TicketController();
        NoteController n = new NoteController();
        CostController c = new CostController();

        List<TypeEquipment> createTypesEquipment = Fakes.createEquipmentTypes();
        for (TypeEquipment typeEquipment : createTypesEquipment) {
            dbTypeEquipment.showSQL().insert(typeEquipment);
        }

        // Popula todas tabelas com fakes
        // Obs: Deve ser feito na ordem correta
        ResponseEntity<ReplyMessage> resp = u.createfakes();
        System.out.println(resp.toString());
        resp = i.createfakes();
        System.out.println(resp.toString());
        resp = e.createfakes();
        System.out.println(resp.toString());
        resp = t.createFakes();
        System.out.println(resp.toString());
        resp = n.createFakes();
        System.out.println(resp.toString());
        resp = c.createFakes();
        System.out.println(resp.toString());

        return new ResponseEntity<>("Banco criado e populado OK!", HttpStatus.OK);
    }

    @RequestMapping("/dbinfo")
    public ResponseEntity<DbInfo> dbInfo() {
        return new ResponseEntity<>(new DbInfo(fileDB.getAbsolutePath(), fileDB.length()), HttpStatus.OK);
    }

    @RequestMapping("/serverinfo")
    public String serverInfo() {
        String info = "";
        Properties properties = System.getProperties();
        Set<Map.Entry<Object, Object>> entrySet = properties.entrySet();
        for (Map.Entry<Object, Object> entry : entrySet) {
            info += entry.getKey() + "=" + entry.getValue() + "<br>";
        }
        return info;
    }

    public class DbInfo {

        private final String dbFilePath;
        private final Long dbFileSise;

        public DbInfo(String dbFilePath, Long dbFileSise) {
            this.dbFilePath = dbFilePath;
            this.dbFileSise = dbFileSise;
        }

        public String getDbFilePath() {
            return dbFilePath;
        }

        public Long getDbFileSise() {
            return dbFileSise;
        }

    }

}
