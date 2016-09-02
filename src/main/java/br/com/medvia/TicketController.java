package br.com.medvia;

import br.com.medvia.db.DBManager;
import br.com.medvia.resources.Equipment;
import br.com.medvia.resources.Ticket;
import br.com.medvia.resources.User;
import br.com.medvia.util.Fakes;
import br.com.medvia.util.ReplyMessage;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Willian
 */
@RestController
@RequestMapping("/api/tickets")
@CrossOrigin
public class TicketController extends AbstractController {

    // order by t.state, t.priority, e.institutionID
    public static final String QUERY_LIST = "select t.id, t.state, t.title, t.description, e.description as equipment, t.dateOcurrence, t.prediction, t.situation, t.priority from Ticket t, Equipment e where t.equipmentID = e.id";

    private static final String GET_DROP = "/drop";
    private static final String GET_CREATEFAKES = "/createfakes";
    private static final String PUT_CLOSE = "/{id}/close";

    public TicketController() {
        System.out.println(TicketController.class.getSimpleName() + " OK!");
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Map<String, Object>>> list() {
        List<Map<String, Object>> selectAll = DBManager.getInstance().getDbTicket().executeQuery(QUERY_LIST);
        return new ResponseEntity<>(selectAll, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ReplyMessage> create(@RequestBody Ticket ticket) {
        System.out.println(ticket.toString());
        ticket.setState("1a");

        // valida campos obrigatórios
        if (ticket.getDescription() == null || ticket.getDescription().isEmpty()) {
            return returnOK("Campo obrigatório não informado: Descrição");
        }

        boolean insert = DBManager.getInstance().getDbTicket().insert(ticket);
        return returnOK(insert ? "Criou novo chamado com sucesso!" : "Não foi possível criar um novo chamdo!");
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Ticket> get(@PathVariable(value = "id") Integer id) {
        System.out.println("ID = " + id);
        return new ResponseEntity<>(DBManager.getInstance().getDbTicket().selectByID(id), HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ReplyMessage> edit(@PathVariable(value = "id") Integer id, @RequestBody Ticket ticket) {
        System.out.println("ID = " + id);
        if (id == null) {
            return returnOK("ID inválido!");
        }
        ticket.setId(id);
        boolean update = DBManager.getInstance().getDbTicket().update(ticket);
        return returnOK(update ? "Update OK!" : "Update FAIL!");
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ReplyMessage> delete(@PathVariable(value = "id") Integer id) {
        System.out.println("ID = " + id);
        if (id == null) {
            return returnOK("ID inválido!");
        }
        Ticket ticket = DBManager.getInstance().getDbTicket().selectByID(id);
        ticket.setState("3e");
        boolean update = DBManager.getInstance().getDbTicket().update(ticket);
        return returnOK(update ? "Delete OK!" : "Delete FAIL!");
    }

    @RequestMapping(path = PUT_CLOSE, method = RequestMethod.PUT)
    public ResponseEntity<ReplyMessage> close(@PathVariable(value = "id") Integer id, @RequestBody Ticket ticket) {
        System.out.println("ID = " + id);
        if (id == null) {
            return returnOK("ID inválido!");
        }
        Ticket ticketOriginal = DBManager.getInstance().getDbTicket().selectByID(id);
        // altera apenas os dados do fechamento
        ticketOriginal.setDateClosing(ticket.getDateClosing());
        ticketOriginal.setNoteClosing(ticket.getNoteClosing());
        ticketOriginal.setState("2f");
        boolean update = DBManager.getInstance().getDbTicket().update(ticketOriginal);
        return returnOK(update ? "Close OK!" : "Close FAIL!");
    }

    @RequestMapping(GET_DROP)
    public ResponseEntity<ReplyMessage> drop() {
        boolean dropAndCreateTable = DBManager.getInstance().getDbTicket().dropAndCreateTable();
        return new ResponseEntity<>(
                new ReplyMessage(dropAndCreateTable ? "Todos chamados deletados com sucesso!" : "Erro ao deletar todos chamados!"),
                HttpStatus.OK);
    }

    @RequestMapping(GET_CREATEFAKES)
    public ResponseEntity<ReplyMessage> createFakes() {
        List<User> users = DBManager.getInstance().getDbUser().selectAll();
        // se ainda não existir nenhum 
        if (users.isEmpty()) {
            return returnOK("Nenhum usuário ainda foi criado!");
        }
        List<Equipment> equipments = DBManager.getInstance().getDbEquipment().selectAll();
        // se ainda não existir nenhum 
        if (equipments.isEmpty()) {
            return returnOK("Nenhum equipamento ainda foi criado!");
        }
        List<Ticket> created = Fakes.createTickets(users, equipments);
        created.stream().forEach((element) -> {
            create(element);
        });
        return returnOK(created.size() + " fakes foram criados com sucesso!");
    }

}
