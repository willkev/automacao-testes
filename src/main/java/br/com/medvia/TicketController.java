package br.com.medvia;

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
 * @author Willian Kirschner willkev@gmail.com
 */
@RestController
@RequestMapping("/api/tickets")
@CrossOrigin
public class TicketController extends AbstractController {

    public static final String QUERY_LIST = "select uO.name openedBy, uR.name responsable, t.id id, t.state state, t.title title, i.description institution,(e.name || ' - ' || e.manufacturer) equipment, t.dateOcurrence dateOcurrence, t.prediction prediction, t.situation situation, t.priority priority from Ticket t, Equipment e, Institution i, (select * from User) uO, (select * from User) uR where t.userId = uO.id and t.responsableId = uR.id and t.equipmentId = e.id and e.institutionId = i.id";
    public static final String QUERY_LIST_ID = "select t.*,e.institutionId from Ticket t, Equipment e where t.equipmentId = e.id and t.id = ";

    private static final String PUT_CLOSE = "/{id}/close";
    private static final String PUT_DELETE = "/{id}/delete";

    private final WkDB<Ticket> db;

    public TicketController() {
        super(TicketController.class.getSimpleName());
        db = new WkDB<>(Ticket.class);
    }

    @RequestMapping(path = "/cookie", method = RequestMethod.GET)
    public ResponseEntity<?> cookie(@CookieValue(value = "cookie1", required = false) String cookie1,
            @CookieValue(value = "ls.userId", required = false) String userId,
            @CookieValue(value = "other", required = false) String other) {
        return returnOK("cookie1=" + cookie1 + "; userId=" + userId + "; other=" + other);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> list() {
        List<Map<String, Object>> selectAll = db.executeQuery(QUERY_LIST);
        return new ResponseEntity<>(selectAll, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ReplyMessage> create(@RequestBody Ticket ticket) {
        // valida campos obrigatórios
        if (!isValueOK(ticket.getUserId(), 1, Integer.MAX_VALUE)) {
            return returnFieldMandatory("Usuário Criador");
        }
        if (!isValueOK(ticket.getResponsableId(), 1, Integer.MAX_VALUE)) {
            return returnFieldMandatory("Usuário Responsável");
        }
        if (!isValueOK(ticket.getDescription())) {
            return returnFieldMandatory("Descrição");
        }
        if (!isValueOK(ticket.getEquipmentId(), 1, Integer.MAX_VALUE)) {
            return returnFieldMandatory("Equipamento");
        }
        ticket.setState("a");
        boolean insert = db.insert(ticket);
        return returnMsg(insert, "Criou novo chamado com sucesso!", "Não foi possível criar um novo chamdo!");
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> get(@PathVariable(value = "id") int id) {
        List<Map<String, Object>> selectOne = db.executeQuery(QUERY_LIST_ID + id);
        return new ResponseEntity<>(selectOne.get(0), HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ReplyMessage> edit(@PathVariable(value = "id") int id, @RequestBody Ticket ticket) {
        // Valida campos
        if (!isValueOK(ticket.getSituation())) {
            return returnFieldMandatory("Situação");
        }
        if (!isValueOK(ticket.getState())) {
            return returnFieldMandatory("Estado");
        }
        if (!isValueOK(ticket.getDateOcurrence())) {
            return returnFieldMandatory("Data de ocorrência");
        }
        if (!isValueOK(ticket.getPrediction())) {
            return returnFieldMandatory("Data de previsão de conserto");
        }
        if (!isValueOK(ticket.getResponsableId(), 1, Integer.MAX_VALUE)) {
            return returnFieldMandatory("Responsável");
        }
        Ticket ticketOriginal = db.selectById(id);
        if (ticketOriginal == null) {
            return returnFail(ID_NOT_FOUND);
        }
        // altera apenas os dados que podem ser alterados
        ticketOriginal.setSituation(ticket.getSituation());
        ticketOriginal.setState(ticket.getState());
        ticketOriginal.setDateOcurrence(ticket.getDateOcurrence());
        ticketOriginal.setPrediction(ticket.getPrediction());
        ticketOriginal.setResponsableId(ticket.getResponsableId());
        boolean update = db.update(ticket);
        return returnMsgUpdate(update);
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
            return returnFail(ID_NOT_FOUND);
        }
        // altera apenas os dados do fechamento
        ticketOriginal.setDateClosing(ticket.getDateClosing());
        ticketOriginal.setNoteClosing(ticket.getNoteClosing());
        ticketOriginal.setState("f");
        boolean update = db.update(ticketOriginal);
        return returnMsg(update, "Close OK!", "Close Fail!");
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
            return returnFail(ID_NOT_FOUND);
        }
        // altera apenas os dados da deleção
        ticketOriginal.setDateRemoving(ticket.getDateRemoving());
        ticketOriginal.setNoteRemoving(ticket.getNoteRemoving());
        ticketOriginal.setState("e");
        boolean delete = db.update(ticketOriginal);
        return returnMsgDelete(delete);
    }

    @RequestMapping(PATH_FAKES)
    public ResponseEntity<ReplyMessage> createFakes() {
        List<User> users = new WkDB<>(User.class).selectAll();
        // se ainda não existir nenhum 
        if (users.isEmpty()) {
            return returnFail("Nenhum usuário ainda foi criado!");
        }
        List<Equipment> equipments = new WkDB<>(Equipment.class).selectAll();
        // se ainda não existir nenhum 
        if (equipments.isEmpty()) {
            return returnFail("Nenhum equipamento ainda foi criado!");
        }
        List<Ticket> created = Fakes.createTickets(users, equipments);
        created.stream().forEach((element) -> {
            create(element);
        });
        return fakesCreated(created.size());
    }

}
