package br.com.medvia;

import br.com.medvia.resources.Greeting;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hi, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/hi")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "Mario") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @RequestMapping("/serverinfo")
    public static String serverInfo() {
        String info = "";
        Properties properties = System.getProperties();
        Set<Map.Entry<Object, Object>> entrySet = properties.entrySet();
        for (Map.Entry<Object, Object> entry : entrySet) {
            info += entry.getKey() + "=" + entry.getValue() + "<br>";
        }
        return info;
    }
}
