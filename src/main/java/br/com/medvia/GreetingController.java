package br.com.medvia;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hi, %s!";
    private final AtomicLong counter = new AtomicLong();
    private final AtomicLong counter2 = new AtomicLong();

    @RequestMapping("/hi")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "Mario") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @RequestMapping("/hi2")
    public Greeting greeting2(@RequestParam(value = "name", defaultValue = "Luigui") String name) {
        return new Greeting(counter2.incrementAndGet(), String.format(template, name));
    }
}
