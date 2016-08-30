package br.com.medvia;

import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    // java.io.tmpdir=C:\Users\willian.kirschner\AppData\Roaming\NetBeans\8.1\apache-tomcat-8.0.27.0_base\temp
    // user.home=C:\Users\willian.kirschner
    //
    // java.io.tmpdir=/var/cache/tomcat8/temp
    // user.home=/usr/share/tomcat8

    public GreetingController() {
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

}
