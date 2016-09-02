package br.com.medvia;

import br.com.medvia.db.DBManager;
import br.com.medvia.resources.Note;
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
public class NoteController extends AbstractController {

    public static final String QUERY_LIST = "select n.id, n.description, u.name as user, n.date from Note n, User u where n.userID = u.id and n.tickteID = ";

    private static final String GET_LIST = "/api/tickets/{id}/notes";
    private static final String GET_GET = "/api/notes/{id}";
    private static final String GET_DROP = "/api/notes/drop";
    private static final String GET_CREATEFAKES = "/api/notes/createfakes";
    private static final String POST_CREATE = "/api/tickets/{id}/notes";
    private static final String PUT_EDIT = "/api/tickets/{idTicket}/notes/{id}";
    private static final String DELETE_DELETE = "/api/notes/{id}";

    public NoteController() {
        System.out.println(NoteController.class.getSimpleName() + " OK!");
    }

    @RequestMapping(path = GET_LIST, method = RequestMethod.GET)
    public ResponseEntity<List<Map<String, Object>>> list(@PathVariable(value = "id") Integer id) {
        List<Map<String, Object>> selectAll = DBManager.getInstance().getDbNote().executeQuery(QUERY_LIST + id);
        return new ResponseEntity<>(selectAll, HttpStatus.OK);
    }

    @RequestMapping(path = POST_CREATE, method = RequestMethod.POST)
    public ResponseEntity<ReplyMessage> create(@PathVariable(value = "id") Integer id, @RequestBody Note note) {
        System.out.println(note.toString());
        // Valida campos obrigatórios
        if (note.getDescription() == null || note.getDescription().isEmpty()) {
            return returnOK("Campo obrigatório não informado: Descrição");
        }
        if (note.getUserID() == null || note.getUserID() < 0) {
            return returnOK("Campo obrigatório não informado: Usuário");
        }

        SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        note.setTickteID(id);
        note.setDate(dateFormater.format(new Date()));
        boolean insert = DBManager.getInstance().getDbNote().insert(note);
        return returnOK(insert ? "Criou nova nota com sucesso!" : "Não foi possível criar nova nota!");
    }

    @RequestMapping(path = GET_GET, method = RequestMethod.GET)
    public ResponseEntity<Note> get(@PathVariable(value = "id") Integer id) {
        System.out.println("ID = " + id);
        return new ResponseEntity<>(DBManager.getInstance().getDbNote().selectByID(id), HttpStatus.OK);
    }

    @RequestMapping(path = PUT_EDIT, method = RequestMethod.PUT)
    public ResponseEntity<ReplyMessage> edit(@PathVariable(value = "idTicket") Integer idTicket,
            @PathVariable(value = "id") Integer id, @RequestBody Note note) {
        if (id == null) {
            return returnOK("ID inválido!");
        }
        Note noteOriginal = DBManager.getInstance().getDbNote().selectByID(id);
        if (noteOriginal == null) {
            return returnOK("Nota não encontrada!");
        }
        // Se informou ID errado para o Ticket
        if (!Objects.equals(noteOriginal.getTickteID(), idTicket)) {
            return returnOK("Nota não encontrada para o Ticket!");
        }
        SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        // Altera apenas alguns campos
        noteOriginal.setDescription(note.getDescription());
        noteOriginal.setDate(dateFormater.format(new Date()));
        boolean update = DBManager.getInstance().getDbNote().update(noteOriginal);
        return returnOK(update ? "Update OK!" : "Update FAIL!");
    }

    @RequestMapping(path = DELETE_DELETE, method = RequestMethod.DELETE)
    public ResponseEntity<ReplyMessage> delete(@PathVariable(value = "id") Integer id) {
        System.out.println("ID = " + id);
        if (id == null) {
            return returnOK("ID inválido!");
        }
        boolean delete = DBManager.getInstance().getDbNote().deleteByID(id);
        // confere se deletou
        if (delete) {
            delete = DBManager.getInstance().getDbNote().selectByID(id) == null;
        }
        return returnOK(delete ? "Delete OK!" : "Delete FAIL!");
    }

    @RequestMapping(GET_DROP)
    public ResponseEntity<ReplyMessage> drop() {
        DBManager.getInstance().getDbNote().dropAndCreateTable();
        return returnOK("Todas notas foram deletados com sucesso!");
    }

    @RequestMapping(GET_CREATEFAKES)
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
            List<Note> created = Fakes.createNotes(t.getId(), users);
            count += created.size();
            created.stream().forEach((element) -> {
                create(element.getTickteID(), element);
            });
        }
        return returnOK(count + " fakes foram criados com sucesso!");
    }

}
