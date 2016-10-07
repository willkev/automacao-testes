package br.com.medvia;

import br.com.medvia.db.WkDB;
import br.com.medvia.resources.Note;
import br.com.medvia.resources.Ticket;
import br.com.medvia.resources.User;
import br.com.medvia.util.Fakes;
import br.com.medvia.util.ReplyMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Willian Kirschner willkev@gmail.com
 */
@RestController
@CrossOrigin
public class NoteController extends AbstractController {

    public static final String QUERY_LIST = "select n.id id,n.description,n.userId userId,u.name user,n.date from Note n,User u where n.userId = u.id and n.tickteId = ";

    private static final String PATH_NOTE = "/api/notes";
    private static final String PATH_NOTE_ID = PATH_NOTE + "/{id}";
    private static final String GET_LIST = "/api/tickets/{id}/notes";
    private static final String POST_CREATE = "/api/tickets/{id}/notes";
    private static final String PUT_EDIT = "/api/tickets/{idTicket}/notes/{id}";

    private final WkDB<Note> db;
    private final WkDB<Ticket> dbT;

    public NoteController() {
        super(NoteController.class.getSimpleName());
        db = new WkDB<>(Note.class);
        dbT = new WkDB<>(Ticket.class);
    }

    @RequestMapping(path = GET_LIST, method = RequestMethod.GET)
    public ResponseEntity<List<Map<String, Object>>> list(@PathVariable(value = "id") int id) {
        List<Map<String, Object>> selectAll = db.executeQuery(QUERY_LIST + id);
        // Adiciona as notas de Exclusão ou Fechamento, se existir
        Ticket ticket = dbT.selectById(id);
        if (ticket != null) {
            // Se possui nota de fechamento
            if (isValueOK(ticket.getNoteClosing())) {
                Map<String, Object> map = new HashMap<>();
                //
                // id=0 Significa que não pode ser alterado/exluido no Frontend
                map.put("id", 0);
                map.put("description", "[Fechamento] " + ticket.getNoteClosing());
                map.put("date", ticket.getDateClosing());
                map.put("userId", ticket.getUserId());
                map.put("user", "UsuarioXYZ");
                selectAll.add(map);
            }
            // Se possui nota de exclusão
            if (isValueOK(ticket.getNoteRemoving())) {
                Map<String, Object> map = new HashMap<>();
                //
                // id=0 Significa que não pode ser alterado/exluido no Frontend
                map.put("id", 0);
                map.put("description", "[Exclusão] " + ticket.getNoteRemoving());
                map.put("date", ticket.getDateRemoving());
                map.put("userId", ticket.getUserId());
                map.put("user", "UsuarioXYZ");
                selectAll.add(map);
            }
        }
        return new ResponseEntity<>(selectAll, HttpStatus.OK);
    }

    @RequestMapping(path = POST_CREATE, method = RequestMethod.POST)
    public ResponseEntity<ReplyMessage> create(@RequestHeader(value = "userId", required = false) String userIdStr,
            @PathVariable(value = "id") int id, @RequestBody Note note) {
        note.setUserId(verifyUser(userIdStr));
        // Valida campos obrigatórios
        if (!isValueOK(note.getDescription())) {
            return returnFieldMandatory("Descrição");
        }
//        if (!isValueOK(note.getUserId())) {
//            return returnFieldMandatory("Usuário");
//        }
        SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        note.setTickteId(id);
        note.setDate(dateFormater.format(new Date()));
        boolean insert = db.insert(note);
        return returnMsg(insert, "Criou nova nota com sucesso!", "Não foi possível criar nova nota!");
    }

    @RequestMapping(path = PATH_NOTE_ID, method = RequestMethod.GET)
    public ResponseEntity<Note> get(@PathVariable(value = "id") int id) {
        return new ResponseEntity<>(db.selectById(id), HttpStatus.OK);
    }

    @RequestMapping(path = PUT_EDIT, method = RequestMethod.PUT)
    public ResponseEntity<ReplyMessage> edit(@PathVariable(value = "idTicket") int idTicket,
            @PathVariable(value = "id") int id, @RequestBody Note note) {
        Note noteOriginal = db.selectById(id);
        if (noteOriginal == null) {
            return returnFail(ID_NOT_FOUND);
        }
        // Se informou ID errado para o Ticket
        if (!Objects.equals(noteOriginal.getTickteId(), idTicket)) {
            return returnFail("Nota não encontrada para o Ticket ID!");
        }
        SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        // Altera apenas alguns campos
        noteOriginal.setDescription(note.getDescription());
        noteOriginal.setDate(dateFormater.format(new Date()));
        boolean update = db.update(noteOriginal);
        return returnMsgUpdate(update);
    }

    @RequestMapping(path = PATH_NOTE_ID, method = RequestMethod.DELETE)
    public ResponseEntity<ReplyMessage> delete(@PathVariable(value = "id") int id) {
        boolean delete = db.deleteById(id);
        // confere se deletou
        if (delete) {
            delete = db.selectById(id) == null;
        }
        return returnMsgDelete(delete);
    }

    @RequestMapping(PATH_NOTE + PATH_FAKES)
    public ResponseEntity<ReplyMessage> createFakes() {
        List<User> users = new WkDB<>(User.class).selectAll();
        // se ainda não existir nenhum 
        if (users.isEmpty()) {
            return returnFail("Nenhum usuário ainda foi criado!");
        }
        List<Ticket> tickets = dbT.selectAll();
        // se ainda não existir nenhum 
        if (tickets.isEmpty()) {
            return returnFail("Nenhum chamado ainda foi criado!");
        }
        int count = 0;
        for (Ticket t : tickets) {
            List<Note> created = Fakes.createNotes(t.getId(), users);
            count += created.size();
            created.stream().forEach((element) -> {
                create("1", element.getTickteId(), element);
            });
        }
        return fakesCreated(count);
    }

}
