package br.com.medvia;

import br.com.medvia.resources.Ticket;
import br.com.medvia.util.FakesTickets;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Willian
 */
@RestController
@CrossOrigin
public class TiketController {

    // OLD
    @RequestMapping("/api/tickets/list")
    public String getAllTickets(@RequestParam(value = "repeat", defaultValue = "1") int repeat) {
        System.out.println("repeat=" + repeat);
        FakesTickets fakesTickets = new FakesTickets();
        return new Gson().toJson(fakesTickets.generate(repeat));
    }

    @RequestMapping("/api/tickets")
    public Ticket[] getAll(@RequestParam(value = "repeat", defaultValue = "1") int repeat) {
        System.out.println("repeat=" + repeat);
        FakesTickets fakesTickets = new FakesTickets();
        return fakesTickets.generate(repeat);
    }

    @RequestMapping(path = "/api/tickets", method = RequestMethod.POST)
    public MessageReturn create(@RequestBody String ticket) {

        return new MessageReturn("Criou novo chamado com sucesso!");
    }
}
