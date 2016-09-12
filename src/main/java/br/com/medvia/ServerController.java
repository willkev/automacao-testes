package br.com.medvia;

import br.com.medvia.db.DBManager;
import br.com.medvia.util.ReplyMessage;
import java.io.File;
import java.util.HashMap;
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
    public ServerController() {
        System.out.println(ServerController.class.getSimpleName() + " OK!");
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

    @RequestMapping("/dbdrop")
    public ResponseEntity<String> dbReset() {
        DBManager.getInstance().dropAndCreateTable();
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
    public ResponseEntity<Map<String, String>> dbInfo() {
        Map<String, String> info = new HashMap<>();
        File fileDB = DBManager.getInstance().getFileDB();
        info.put("dbFilePath", fileDB.getAbsolutePath());
        info.put("dbFileSise", "" + fileDB.length());
        return new ResponseEntity<>(info,
                HttpStatus.OK);
    }

}
