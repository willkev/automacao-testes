package br.com.medvia;

import br.com.medvia.db.WkDB;
import br.com.medvia.resources.Ticket;
import br.com.medvia.util.FakesTickets;
import br.com.medvia.util.ReplyMessage;
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

    private static final String METHOD_CREATEFAKES = "/api/tickets/createfakes";
    private static final String METHOD_LIST = "/api/tickets";
    private static final String METHOD_CREATE = "/api/tickets";
    private static final String METHOD_EDIT = "/api/tickets/{id}";

    private final WkDB<Ticket> db;

    public TiketController() {
        System.out.println("TiketController OK!");
        db = new WkDB<>(Ticket.class);
        boolean createTable = db.createTable();
        System.out.println("Create new table 'Ticket' ? " + createTable);
    }

    // OLD
    @RequestMapping(METHOD_LIST + "/list")
    public ResponseEntity<String> getAllTickets() {
        return new ResponseEntity<>(new Gson().toJson(db.selectAll(null)), HttpStatus.OK);
    }

    @RequestMapping(path = METHOD_LIST, method = RequestMethod.GET)
    public ResponseEntity<List<Ticket>> list() {
        return new ResponseEntity<>(db.selectAll(null), HttpStatus.OK);
    }

    @RequestMapping(path = METHOD_CREATE, method = RequestMethod.POST)
    public ResponseEntity<ReplyMessage> create(@RequestBody Ticket ticket) {
        System.out.println(ticket.toString());
        db.insert(ticket);
        return new ResponseEntity<>(
                new ReplyMessage("Criou novo chamado com sucesso!"),
                HttpStatus.OK);
    }

    @RequestMapping(METHOD_CREATEFAKES)
    public ResponseEntity<ReplyMessage> getAllTickets(@RequestParam(value = "repeat", defaultValue = "1") int repeat) {
        System.out.println("repeat=" + repeat);
        List<Ticket> ticketsFakes = FakesTickets.createFakes(repeat);
        for (Ticket ticketFake : ticketsFakes) {
            db.insert(ticketFake);
        }
        return new ResponseEntity<>(
                new ReplyMessage("Fakes foram criados com sucesso!"),
                HttpStatus.OK);
    }

}
