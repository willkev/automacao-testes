package br.com.medvia;

import br.com.medvia.db.DBManager;
import br.com.medvia.db.WkDB;
import br.com.medvia.resources.Equipment;
import br.com.medvia.resources.Ticket;
import br.com.medvia.resources.User;
import br.com.medvia.util.Fakes;
import br.com.medvia.util.ReplyMessage;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
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

    public static final String QUERY_LIST = "select uO.name openedBy,uR.name responsable,t.id id,t.state state,t.title title,i.description institution,(e.description || ' - ' || e.manufacturer) equipment,t.dateOcurrence dateOcurrence,t.prediction prediction,t.situation situation,t.priority priority from Ticket t, Equipment e, Institution i, (select * from User) uO, (select * from User) uR where t.openedById = uO.id and t.responsableId = uR.id and t.equipmentId = e.id and e.institutionId = i.id";

    private static final String PUT_CLOSE = "/{id}/close";
    private static final String PUT_DELETE = "/{id}/delete";

    private final WkDB<Ticket> db;

    public TicketController() {
        System.out.println(TicketController.class.getSimpleName() + " OK!");
        db = new WkDB<>(Ticket.class);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Map<String, Object>>> list() {
        List<Map<String, Object>> selectAll = db.executeQuery(QUERY_LIST);
        return new ResponseEntity<>(selectAll, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ReplyMessage> create(@CookieValue("userId") String userId, @RequestBody Ticket ticket) {
        // valida campos obrigatórios
        int userIdInt = 0;
        try {
            userIdInt = Integer.parseInt(userId);
            if (!isValueOK(userIdInt, 1, Integer.MAX_VALUE)) {
                return returnFieldMandatory("userId");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnBadRequest(e.getMessage());
        }
        if (!isValueOK(ticket.getDescription())) {
            return returnFieldMandatory("Descrição");
        }
        ticket.setState("a");
        ticket.setOpenedById(userIdInt);
        boolean insert = db.insert(ticket);
        if (insert) {
            return returnOK("Criou novo chamado com sucesso!");
        }
        return returnBadRequest("Não foi possível criar um novo chamdo!");
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Ticket> get(@PathVariable(value = "id") int id) {
        return new ResponseEntity<>(db.selectById(id), HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ReplyMessage> edit(@PathVariable(value = "id") int id, @RequestBody Ticket ticket) {
        ticket.setId(id);
        boolean update = db.update(ticket);
        if (update) {
            return returnOK("Update OK!");
        }
        return returnBadRequest("Update Fail!");
    }

    @RequestMapping(path = PUT_CLOSE, method = RequestMethod.PUT)
    public ResponseEntity<ReplyMessage> close(@PathVariable(value = "id") int id, @RequestBody Ticket ticket) {
        if (!isValueOK(ticket.getDateClosing())) {
            return returnFieldMandatory("Data de Fechamento");
        }
        if (!isValueOK(ticket.getNoteClosing())) {
            return returnFieldMandatory("Nota de Fechamento");
        }
        Ticket ticketOriginal = db.selectById(id);
        if (ticketOriginal == null) {
            return returnBadRequest(ID_NOT_FOUND);
        }
        // altera apenas os dados do fechamento
        ticketOriginal.setDateClosing(ticket.getDateClosing());
        ticketOriginal.setNoteClosing(ticket.getNoteClosing());
        ticketOriginal.setState("f");
        boolean update = db.update(ticketOriginal);
        if (update) {
            return returnOK("Close OK!");
        }
        return returnBadRequest("Close Fail!");
    }

    @RequestMapping(path = PUT_DELETE, method = RequestMethod.PUT)
    public ResponseEntity<ReplyMessage> delete(@PathVariable(value = "id") int id, @RequestBody Ticket ticket) {
        if (!isValueOK(ticket.getDateRemoving())) {
            return returnFieldMandatory("Data de Exclusão");
        }
        if (!isValueOK(ticket.getNoteRemoving())) {
            return returnFieldMandatory("Nota de Exclusão");
        }
        Ticket ticketOriginal = db.selectById(id);
        if (ticketOriginal == null) {
            return returnBadRequest(ID_NOT_FOUND);
        }
        // altera apenas os dados da deleção
        ticketOriginal.setDateRemoving(ticket.getDateRemoving());
        ticketOriginal.setNoteRemoving(ticket.getNoteRemoving());
        ticketOriginal.setState("e");
        boolean update = db.update(ticketOriginal);
        if (update) {
            return returnOK("Delete OK!");
        }
        return returnBadRequest("Delete Fail!");
    }

    @RequestMapping(PATH_DROP)
    public ResponseEntity<ReplyMessage> drop() {
        boolean dropAndCreateTable = db.dropAndCreateTable();
        return new ResponseEntity<>(
                new ReplyMessage(dropAndCreateTable ? "Todos chamados deletados com sucesso!" : "Erro ao deletar todos chamados!"),
                HttpStatus.OK);
    }

    @RequestMapping(PATH_FAKES)
    public ResponseEntity<ReplyMessage> createFakes() {
        List<User> users = DBManager.getInstance().getDbUser().selectAll();
        // se ainda não existir nenhum 
        if (users.isEmpty()) {
            return returnBadRequest("Nenhum usuário ainda foi criado!");
        }
        List<Equipment> equipments = DBManager.getInstance().getDbEquipment().selectAll();
        // se ainda não existir nenhum 
        if (equipments.isEmpty()) {
            return returnBadRequest("Nenhum equipamento ainda foi criado!");
        }
        List<Ticket> created = Fakes.createTickets(users, equipments);
        created.stream().forEach((element) -> {
            create("1", element);
        });
        return returnOK(created.size() + " fakes foram criados com sucesso!");
    }

}
