package br.com.medvia;

import br.com.medvia.db.DBManager;
import br.com.medvia.resources.User;
import br.com.medvia.util.Fakes;
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
public class UserController {

    private static final String METHOD_LIST = "/api/users";
    private static final String METHOD_CREATE = "/api/users";
    private static final String METHOD_EDIT = "/api/users/{id}";
    private static final String METHOD_DROP = "/api/users/drop";
    private static final String METHOD_CREATEFAKES = "/api/users/createfakes";

    public UserController() {
        System.out.println(UserController.class.getSimpleName() + " OK!");
    }

    @RequestMapping(path = METHOD_LIST, method = RequestMethod.GET)
    public ResponseEntity<List<User>> list() {
        List<User> selectAll = DBManager.getInstance().getDbUser().selectAll();
        return new ResponseEntity<>(selectAll, HttpStatus.OK);
    }

    @RequestMapping(path = METHOD_CREATE, method = RequestMethod.POST)
    public ResponseEntity<ReplyMessage> create(@RequestBody User user) {
        System.out.println(user.toString());
        boolean insert = DBManager.getInstance().getDbUser().insert(user);
        return new ResponseEntity<>(
                new ReplyMessage(insert ? "Criou novo usuário com sucesso!" : "Não foi possível criar um novo usuário!"),
                HttpStatus.OK);
    }

    @RequestMapping(path = METHOD_EDIT, method = RequestMethod.PUT)
    public ResponseEntity<ReplyMessage> edit(@RequestBody User user,
            @RequestParam(value = "id", defaultValue = "-1") int id) {
        System.out.println("ID = " + id);
        if (id < 0) {
            return new ResponseEntity<>(new ReplyMessage("ID inválido!"), HttpStatus.OK);
        }
        user.setId(id);
        boolean update = DBManager.getInstance().getDbUser().update(user);
        return new ResponseEntity<>(
                new ReplyMessage(update ? "Update OK!" : "Update FAIL!"),
                HttpStatus.OK);
    }

    @RequestMapping(METHOD_DROP)
    public ResponseEntity<ReplyMessage> drop() {
        DBManager.getInstance().getDbUser().dropAndCreateTable();
        return new ResponseEntity<>(
                new ReplyMessage("Todos usuários foram deletados com sucesso!"),
                HttpStatus.OK);
    }

    @RequestMapping(METHOD_CREATEFAKES)
    public ResponseEntity<ReplyMessage> createfakes() {
        List<User> created = Fakes.createUsers();
        created.stream().forEach((element) -> {
            create(element);
        });
        return new ResponseEntity<>(
                new ReplyMessage(created.size() + " fakes foram criados com sucesso!"),
                HttpStatus.OK);
    }

}
