package br.com.medvia;

import static br.com.medvia.AbstractController.PATH_DROP;
import br.com.medvia.db.DBManager;
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
 * @author Willian
 */
@RestController
@CrossOrigin
public class CostController extends AbstractController {

    public static final String QUERY_LIST = "select c.Id Id,c.description,c.userId userId,u.name user,c.date from Cost c,User u where c.userId = u.Id and c.tickteId = ";

    private static final String PATH_COST = "/api/costs";
    private static final String PATH_COST_ID = PATH_COST + "/{id}";
    private static final String PATH_LIST = "/api/tickets/{id}/costs";
    private static final String PATH_CREATE = "/api/tickets/{id}/costs";
    private static final String PATH_EDIT = "/api/tickets/{idTicket}/costs/{id}";

    public CostController() {
        System.out.println(CostController.class.getSimpleName() + " OK!");
    }

    @RequestMapping(path = PATH_LIST, method = RequestMethod.GET)
    public ResponseEntity<List<Map<String, Object>>> listByTicket(@PathVariable(value = "id") int id) {
        List<Map<String, Object>> selectAll = DBManager.getInstance().getDbCost().executeQuery(QUERY_LIST + id);
        return new ResponseEntity<>(selectAll, HttpStatus.OK);
    }

    @RequestMapping(path = PATH_CREATE, method = RequestMethod.POST)
    public ResponseEntity<ReplyMessage> create(@PathVariable(value = "id") int id, @RequestBody Cost cost) {
        // Valida campos obrigatórios
        if (!isValueOK(cost.getCost())) {
            return returnOK("Campo obrigatório não informado: Valor");
        }
        if (!isValueOK(cost.getDescription())) {
            return returnOK("Campo obrigatório não informado: Descrição");
        }
        if (!isValueOK(cost.getUserId())) {
            return returnOK("Campo obrigatório não informado: Usuário");
        }
        SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        cost.setTickteId(id);
        cost.setDate(dateFormater.format(new Date()));
        boolean insert = DBManager.getInstance().getDbCost().insert(cost);
        return returnOK(insert ? "Criou novo custo com sucesso!" : "Não foi possível criar novo custo!");
    }

    @RequestMapping(path = PATH_EDIT, method = RequestMethod.PUT)
    public ResponseEntity<ReplyMessage> edit(@PathVariable(value = "idTicket") int idTicket,
            @PathVariable(value = "id") int id, @RequestBody Cost cost) {
        // Valida campos obrigatórios
        if (!isValueOK(cost.getCost())) {
            return returnOK("Campo obrigatório não informado: Valor");
        }
        if (!isValueOK(cost.getDescription())) {
            return returnOK("Campo obrigatório não informado: Descrição");
        }        
        Cost costOriginal = DBManager.getInstance().getDbCost().selectById(id);
        if (costOriginal == null) {
            return returnOK("ID de custo não encontrado!");
        }
        // Se informou ID errado para o Ticket
        if (!Objects.equals(costOriginal.getTickteId(), idTicket)) {
            return returnOK("Custo não encontrada para o Ticket ID!");
        }
        SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        // Altera apenas alguns campos
        costOriginal.setCost(cost.getCost());
        costOriginal.setDescription(cost.getDescription());
        costOriginal.setDate(dateFormater.format(new Date()));
        boolean update = DBManager.getInstance().getDbCost().update(costOriginal);
        return returnOK(update ? "Update OK!" : "Update FAIL!");
    }

    @RequestMapping(path = PATH_COST_ID, method = RequestMethod.GET)
    public ResponseEntity<Cost> getById(@PathVariable(value = "id") int id) {
        return new ResponseEntity<>(DBManager.getInstance().getDbCost().selectById(id), HttpStatus.OK);
    }

    @RequestMapping(PATH_COST + PATH_DROP)
    public ResponseEntity<ReplyMessage> drop() {
        DBManager.getInstance().getDbCost().dropAndCreateTable();
        return returnOK("Todos custos foram deletados com sucesso!");
    }

    @RequestMapping(PATH_COST + PATH_FAKES)
    public ResponseEntity<ReplyMessage> createFakes() {
        List<User> users = DBManager.getInstance().getDbUser().selectAll();
        // se ainda não existir nenhum 
        if (users.isEmpty()) {
            return returnOK("Nenhum usuário ainda foi criado!");
        }
        List<Ticket> tickets = DBManager.getInstance().getDbTicket().selectAll();
        // se ainda não existir nenhum 
        if (tickets.isEmpty()) {
            return returnOK("Nenhum chamado ainda foi criado!");
        }
        int count = 0;
        for (Ticket t : tickets) {
            List<Cost> created = Fakes.createCosts(t.getId(), users);
            count += created.size();
            created.stream().forEach((element) -> {
                create(element.getTickteId(), element);
            });
        }
        return returnOK(count + " fakes foram criados com sucesso!");
    }

}
