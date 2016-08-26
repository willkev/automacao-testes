package br.com.medvia;

import br.com.medvia.resources.Ticket;
import br.com.medvia.util.FakesTickets;
import com.google.gson.Gson;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public TiketController() {
        System.out.println("TiketController OK!");
    }

    // OLD
    @RequestMapping("/api/tickets/list")
    public ResponseEntity<String> getAllTickets(@RequestParam(value = "repeat", defaultValue = "1") int repeat) {
        System.out.println("repeat=" + repeat);
        FakesTickets fakesTickets = new FakesTickets();
        return new ResponseEntity<>(
                new Gson().toJson(fakesTickets.generate(repeat)),
                HttpStatus.OK);
    }

    @RequestMapping(path = "/api/tickets", method = RequestMethod.GET)
    public ResponseEntity<List<Ticket>> list(@RequestParam(value = "repeat", defaultValue = "1") int repeat) {
        System.out.println("repeat=" + repeat);
        FakesTickets fakesTickets = new FakesTickets();
        return new ResponseEntity<>(
                fakesTickets.generate(repeat),
                HttpStatus.OK);
    }

    @RequestMapping(path = "/api/tickets", method = RequestMethod.POST)
    public ResponseEntity<ReplyMessage> create(@RequestBody Ticket ticket) {
        System.out.println(ticket.toString());
        return new ResponseEntity<>(
                new ReplyMessage("Criou novo chamado com sucesso!"),
                HttpStatus.OK);
    }

}
