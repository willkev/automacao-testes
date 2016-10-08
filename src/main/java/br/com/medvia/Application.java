package br.com.medvia;

import br.com.medvia.db.WkDB;
import java.io.File;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n
 java.io.tmpdir=C:\Users\willian.kirschner\AppData\Roaming\NetBeans\8.1\apache-tomcat-8.0.27.0_base\temp
 user.home=C:\Users\willian.kirschner

 java.io.tmpdir=/var/cache/tomcat8/temp
 user.home=/usr/share/tomcat8
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    public Application() {
        System.out.println("Medvia started OK!");
        //String propJavaTmp = System.getProperty("java.io.tmpdir");
        String dir = System.getProperty("user.home");
        File fileDB = new File(dir, "medvia.db");
        System.out.println("fileDB=" + fileDB.getAbsolutePath());
        WkDB.setFileDB(fileDB);
        File dirQC = new File(dir, "quality-control");
        dirQC.mkdir();
        QualityControlController.dirPDF = dirQC;
                
        ServerController sc = new ServerController();
        sc.dbCreate();
    }

}
