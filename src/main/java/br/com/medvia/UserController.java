package br.com.medvia;

import br.com.medvia.db.WkDB;
import br.com.medvia.db.WkDB.Where;
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
 * @author Willian Kirschner willkev@gmail.com
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController extends AbstractController {

    public static final String QUERY_LIST = "select id, name from User";

    private final WkDB<User> db;

    public UserController() {
        super(UserController.class.getSimpleName());
        db = new WkDB<>(User.class);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> list() {
        List<Map<String, Object>> selectAll = db.executeQuery(QUERY_LIST);
        return new ResponseEntity<>(selectAll, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ReplyMessage> create(@RequestBody User user) {
        boolean insert = db.insert(user);
        return returnMsg(insert, "Criou novo usuário com sucesso!", "Não foi possível criar um novo usuário!");
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ReplyMessage> edit(@RequestBody User user, @PathVariable(value = "id") int id) {
        user.setId(id);
        boolean update = db.update(user);
        return returnMsgUpdate(update);
    }

    // required = false, para não expor a assinatura do método de login
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public User login(@RequestBody(required = false) User user) {
        if (user == null || !isValueOK(user.getEmail()) || !isValueOK(user.getPassword())) {
            // força dar uma exeção de permissão!
            verifyUser(null);
        }
        List<User> select = db.selectAll(
                Where.fields("email", "password"),
                Where.conditions("=", "="),
                Where.values(user.getEmail(), user.getPassword()), null);
        if (select.isEmpty()) {
            // força dar uma exeção de permissão!
            verifyUser(null);
        }
        // Remove a senha antes de retornar!
        User get = select.get(0);
        get.setPassword("");
        return get;
    }

    @RequestMapping(PATH_FAKES)
    public ResponseEntity<ReplyMessage> createFakes() {
        List<User> created = Fakes.createUsers();
        created.stream().forEach((element) -> {
            create(element);
        });
        return fakesCreated(created.size());
    }

}
