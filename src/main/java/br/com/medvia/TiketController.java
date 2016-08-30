package br.com.medvia;

import br.com.medvia.db.DBManager;
import br.com.medvia.resources.Ticket;
import br.com.medvia.util.FakesTickets;
import br.com.medvia.util.ReplyMessage;
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
    private static final String METHOD_DROP = "/api/tickets/drop";
    private static final String METHOD_EDIT = "/api/tickets/{id}";

    public TiketController() {
        System.out.println("TiketController OK!");
    }

    @RequestMapping(path = METHOD_LIST, method = RequestMethod.GET)
    public ResponseEntity<List<Ticket>> list() {
        List<Ticket> selectAll = DBManager.getInstance().getDbTicket().selectAll(null);
//        for (Ticket t : selectAll) {
//        };
        return new ResponseEntity<>(selectAll, HttpStatus.OK);
    }

    @RequestMapping(path = METHOD_CREATE, method = RequestMethod.POST)
    public ResponseEntity<ReplyMessage> create(@RequestBody Ticket ticket) {
        System.out.println(ticket.toString());
        ticket.setState("a");
        boolean insert = DBManager.getInstance().getDbTicket().insert(ticket);
        return new ResponseEntity<>(
                new ReplyMessage((insert ? "Insert ok," : "Insert FAIL,") + " criou novo chamado com sucesso!"),
                HttpStatus.OK);
    }

    @RequestMapping(path = METHOD_EDIT, method = RequestMethod.PUT)
    public ResponseEntity<ReplyMessage> edit(@RequestBody Ticket ticket,
            @RequestParam(value = "id", defaultValue = "-1") int id) {
        System.out.println("ID = " + id);
        ticket.setID(id);
        boolean update = DBManager.getInstance().getDbTicket().update(ticket);
        return new ResponseEntity<>(
                new ReplyMessage(update ? "Update OK!" : "Update FAIL!"),
                HttpStatus.OK);
    }

    @RequestMapping(METHOD_DROP)
    public ResponseEntity<ReplyMessage> drop() {
        DBManager.getInstance().dropAndCreateTable();
        return new ResponseEntity<>(
                new ReplyMessage("Todos tickets foram deletados com sucesso!"),
                HttpStatus.OK);
    }

    @RequestMapping(METHOD_CREATEFAKES)
    public ResponseEntity<ReplyMessage> createfakes(@RequestParam(value = "repeat", defaultValue = "1") int repeat) {
        int generated = FakesTickets.createFakes(repeat,
                DBManager.getInstance().getDbTicket(),
                DBManager.getInstance().getDbUser(),
                DBManager.getInstance().getDbEquipment());
        return new ResponseEntity<>(
                new ReplyMessage(generated + " fakes foram criados com sucesso!"),
                HttpStatus.OK);
    }

    /*
     select t.title, t.description, t.priority, t.situation, t.state, t.dateOcurrence, t.prediction, e.description
     from Ticket t, Equipment e where t.equipment = e.ID    
     */
}
