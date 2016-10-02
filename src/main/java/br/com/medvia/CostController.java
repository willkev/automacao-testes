package br.com.medvia;

import br.com.medvia.db.WkDB;
import br.com.medvia.resources.Cost;
import br.com.medvia.resources.Ticket;
import br.com.medvia.resources.User;
import br.com.medvia.util.Fakes;
import br.com.medvia.util.ReplyMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
 * @author Willian Kirschner willkev@gmail.com
 */
@RestController
@CrossOrigin
public class CostController extends AbstractController {

    public static final String QUERY_LIST = "select c.id,c.value,c.description,c.userId,u.name user,c.date from Cost c,User u where c.userId = u.id and c.tickteId = ";

    private static final String PATH_COST = "/api/costs";
    private static final String PATH_COST_ID = PATH_COST + "/{id}";
    private static final String PATH_LIST = "/api/tickets/{id}/costs";
    private static final String PATH_CREATE = "/api/tickets/{id}/costs";
    private static final String PATH_EDIT = "/api/tickets/{idTicket}/costs/{id}";

    private final WkDB<Cost> db;

    public CostController() {
        super(CostController.class.getSimpleName());
        db = new WkDB<>(Cost.class);
    }

    @RequestMapping(path = PATH_LIST, method = RequestMethod.GET)
    public ResponseEntity<List<Map<String, Object>>> listByTicket(@PathVariable(value = "id") int id) {
        List<Map<String, Object>> selectAll = db.executeQuery(QUERY_LIST + id);
        return new ResponseEntity<>(selectAll, HttpStatus.OK);
    }

    @RequestMapping(path = PATH_CREATE, method = RequestMethod.POST)
    public ResponseEntity<ReplyMessage> create(@PathVariable(value = "id") int id, @RequestBody Cost cost) {
        // Valida campos obrigatórios
        if (!isValueOK(cost.getValue())) {
            return returnFieldMandatory("Valor");
        }
        if (!isValueOK(cost.getDescription())) {
            return returnFieldMandatory("Descrição");
        }
        if (!isValueOK(cost.getUserId(), 1, Integer.MAX_VALUE)) {
            return returnFieldMandatory("Usuário");
        }
        SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        cost.setTickteId(id);
        cost.setDate(dateFormater.format(new Date()));
        boolean insert = db.insert(cost);
        return returnMsg(insert, "Criou novo custo com sucesso!", "Não foi possível criar novo custo!");
    }

    @RequestMapping(path = PATH_EDIT, method = RequestMethod.PUT)
    public ResponseEntity<ReplyMessage> edit(@PathVariable(value = "idTicket") int idTicket,
            @PathVariable(value = "id") int id, @RequestBody Cost cost) {
        // Valida campos obrigatórios
        if (!isValueOK(cost.getValue())) {
            return returnFieldMandatory("Valor");
        }
        if (!isValueOK(cost.getDescription())) {
            return returnFieldMandatory("Descrição");
        }
        Cost costOriginal = db.selectById(id);
        if (costOriginal == null) {
            return returnFail(ID_NOT_FOUND);
        }
        // Se informou ID errado para o Ticket
        if (!Objects.equals(costOriginal.getTickteId(), idTicket)) {
            return returnFail("Custo não encontrado para o Ticket ID!");
        }
        SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        // Altera apenas alguns campos
        costOriginal.setValue(cost.getValue());
        costOriginal.setDescription(cost.getDescription());
        costOriginal.setDate(dateFormater.format(new Date()));
        boolean update = db.update(costOriginal);
        return returnMsgUpdate(update);
    }

    @RequestMapping(path = PATH_COST_ID, method = RequestMethod.GET)
    public ResponseEntity<Cost> getById(@PathVariable(value = "id") int id) {
        return new ResponseEntity<>(db.selectById(id), HttpStatus.OK);
    }

    @RequestMapping(path = PATH_COST_ID, method = RequestMethod.DELETE)
    public ResponseEntity<ReplyMessage> delete(@PathVariable(value = "id") int id) {
        boolean delete = db.deleteById(id);
        // confere se deletou
        if (delete) {
            delete = db.selectById(id) == null;
        }
        return returnMsgDelete(delete);
    }

    @RequestMapping(PATH_COST + PATH_FAKES)
    public ResponseEntity<ReplyMessage> createFakes() {
        List<User> users = new WkDB<>(User.class).selectAll();
        // se ainda não existir nenhum 
        if (users.isEmpty()) {
            return returnFail("Nenhum usuário ainda foi criado!");
        }
        List<Ticket> tickets = new WkDB<>(Ticket.class).selectAll();
        // se ainda não existir nenhum 
        if (tickets.isEmpty()) {
            return returnFail("Nenhum chamado ainda foi criado!");
        }
        int count = 0;
        for (Ticket t : tickets) {
            List<Cost> created = Fakes.createCosts(t.getId(), users);
            count += created.size();
            created.stream().forEach((element) -> {
                create(element.getTickteId(), element);
            });
        }
        return fakesCreated(count);
    }

}
