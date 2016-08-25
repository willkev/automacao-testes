package br.com.medvia;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    // java.io.tmpdir=C:\Users\willian.kirschner\AppData\Roaming\NetBeans\8.1\apache-tomcat-8.0.27.0_base\temp
    // user.home=C:\Users\willian.kirschner
    //
    // java.io.tmpdir=/var/cache/tomcat8/temp
    // user.home=/usr/share/tomcat8
    private final String propJavaTmp = System.getProperty("java.io.tmpdir");
    private final String propUserHome = System.getProperty("user.home");

    public GreetingController() {
        System.out.println("java.io.tmpdir=" + propJavaTmp);
        System.out.println("user.home=" + propUserHome);
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

    @RequestMapping("/read/txt")
    public ArrayList<String> readTxt(@RequestParam(value = "path", defaultValue = "0") int path) {
        File pathOne = new File(propJavaTmp);
        File pathTwo = new File(propUserHome);
        ArrayList<String> files = new ArrayList<String>((int) (pathOne.length() + pathTwo.length()));
        if (path == 0 || path == 1) {
            for (File file : pathOne.listFiles()) {
                files.add(file.getAbsolutePath());
            }
        }
        if (path == 0 || path == 2) {
            for (File file : pathTwo.listFiles()) {
                files.add(file.getAbsolutePath());
            }
        }
        return files;
    }

    @RequestMapping(path = "/create/txt", method = RequestMethod.POST)
    public String createTxt() {
        return "Create onw="
                + createFile(propJavaTmp)
                + ", Create two="
                + createFile(propUserHome);
    }

    private boolean createFile(String propJavaTmp) {
        try {
            File javaTmp = new File(propJavaTmp, System.currentTimeMillis() + ".txt");
            boolean status = javaTmp.createNewFile();
            System.out.format("Create '%s' ? %s\n", propJavaTmp, status);
            return status;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
