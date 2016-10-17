package br.com.medvia;

import br.com.medvia.db.WkDB;
import br.com.medvia.mail.EmailSender;
import br.com.medvia.mail.GmailSimple;
import br.com.medvia.resources.Cost;
import br.com.medvia.resources.Equipment;
import br.com.medvia.resources.Institution;
import br.com.medvia.resources.Note;
import br.com.medvia.resources.NoteQualityControl;
import br.com.medvia.resources.QualityControl;
import br.com.medvia.resources.Ticket;
import br.com.medvia.resources.TypeEquipment;
import br.com.medvia.resources.User;
import br.com.medvia.util.Fakes;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController extends AbstractController {

    private final List<WkDB> dbList = new ArrayList<>();
    private final WkDB<Ticket> dbTicket;
    private final WkDB<Equipment> dbEquipment;
    private final WkDB<User> dbUser;
    private final WkDB<Note> dbNote;
    private final WkDB<Cost> dbCost;
    private final WkDB<Institution> dbInstitution;
    private final WkDB<TypeEquipment> dbTypeEquipment;
    private final WkDB<QualityControl> dbQualityControl;
    private final WkDB<NoteQualityControl> dbNoteQualityControl;

    public ServerController() {
        super(ServerController.class.getSimpleName());

        dbTicket = new WkDB<>(Ticket.class);
        dbList.add(dbTicket);
        dbEquipment = new WkDB<>(Equipment.class);
        dbList.add(dbEquipment);
        dbUser = new WkDB<>(User.class);
        dbList.add(dbUser);
        dbNote = new WkDB<>(Note.class);
        dbList.add(dbNote);
        dbCost = new WkDB<>(Cost.class);
        dbList.add(dbCost);
        dbInstitution = new WkDB<>(Institution.class);
        dbList.add(dbInstitution);
        dbTypeEquipment = new WkDB<>(TypeEquipment.class);
        dbList.add(dbTypeEquipment);
        dbQualityControl = new WkDB<>(QualityControl.class);
        dbList.add(dbQualityControl);
        dbNoteQualityControl = new WkDB<>(NoteQualityControl.class);
        dbList.add(dbNoteQualityControl);

        // se o arquivo ainda não existir
        if (!WkDB.getFileDB().exists() || WkDB.getFileDB().length() < 1) {
            for (WkDB db : dbList) {
                db.createTable();
            }
        }
    }

    @RequestMapping("/dbdrop")
    public ResponseEntity<String> dbReset() {
        for (WkDB db : dbList) {
            db.dropAndCreateTable();
        }
        System.out.println("dropAndCreateTable!");
        return new ResponseEntity<>("DB droped OK!", HttpStatus.OK);
    }

    @RequestMapping("/dbcreate")
    public ResponseEntity<String> dbCreateFakes() {
        return dbCreate0(false);
    }

    @RequestMapping("/dbcreatefakes")
    public ResponseEntity<String> dbCreate() {
        return dbCreate0(true);
    }

    private ResponseEntity<String> dbCreate0(boolean createFakes) {
        dbReset();

        UserController u = new UserController();
        InstitutionController i = new InstitutionController();
        EquipmentController e = new EquipmentController();
        TicketController t = new TicketController();
        NoteController n = new NoteController();
        CostController c = new CostController();
        QualityControlController q = new QualityControlController();
        NoteQualityControlController nqc = new NoteQualityControlController();

        List<TypeEquipment> createTypesEquipment = Fakes.createTypesEquipment();
        for (TypeEquipment typeEquipment : createTypesEquipment) {
            dbTypeEquipment.showSQL().insert(typeEquipment);
        }
        System.out.println(i.createFakes().toString());

        if (createFakes) {
            // Popula todas tabelas com fakes
            // Obs: Deve ser feito na ordem correta
            System.out.println(u.createFakes().toString());
            System.out.println(e.createFakes().toString());
            System.out.println(t.createFakes().toString());
            System.out.println(n.createFakes().toString());
            System.out.println(c.createFakes().toString());
            System.out.println(q.createFakes().toString());
            System.out.println(nqc.createFakes().toString());
        }
        return new ResponseEntity<>("Banco criado e populado OK!", HttpStatus.OK);
    }

    @RequestMapping("/serverinfo")
    public Set<Map.Entry<Object, Object>> serverInfo() {
        return System.getProperties().entrySet();
    }

    @RequestMapping("/dbinfo")
    public ResponseEntity<DbInfo> dbInfo() {
        return new ResponseEntity<>(new DbInfo(WkDB.getFileDB().getAbsolutePath(), WkDB.getFileDB().length()), HttpStatus.OK);
    }

    @RequestMapping(path = "/dbfile", method = RequestMethod.GET)
    public ResponseEntity<?> getDbFile() throws IOException {
        return downloadFile(WkDB.getFileDB(), MediaType.APPLICATION_OCTET_STREAM);
    }

    @RequestMapping(path = "/email", method = RequestMethod.GET)
    public ResponseEntity<?> email(@RequestParam(value = "subject") String subject,
            @RequestParam(value = "content") String content) {
        EmailSender email = new EmailSender();
        return returnMsg(email.send("willkev@gmail.com", subject, content),
                "Email sent!", "Email FAIL!");
    }

    @RequestMapping(path = "/email1", method = RequestMethod.GET)
    public ResponseEntity<?> email1() {
        GmailSimple email = new GmailSimple();
        email.send();
        return returnOK("Email sent...");
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
