package br.com.medvia;

import br.com.medvia.db.DBManager;
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

    @RequestMapping("/dbreset")
    public ResponseEntity<String> dbReset() {
        DBManager.getInstance().dropAndCreateTable();
        return new ResponseEntity<>("Banco resetado OK!", HttpStatus.OK);
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
