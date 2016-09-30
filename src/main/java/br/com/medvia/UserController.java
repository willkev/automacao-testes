package br.com.medvia;

import br.com.medvia.db.WkDB;
import br.com.medvia.resources.User;
import br.com.medvia.util.Fakes;
import br.com.medvia.util.ReplyMessage;
import java.util.List;
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

    private final WkDB<User> db;
    
    public UserController() {
        super(UserController.class.getSimpleName());
        db = new WkDB<>(User.class);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<User>> list() {
        List<User> selectAll = db.selectAll();
        return new ResponseEntity<>(selectAll, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ReplyMessage> create(@RequestBody User user) {
        boolean insert = db.insert(user);
        return new ResponseEntity<>(
                new ReplyMessage(insert ? "Criou novo usuário com sucesso!" : "Não foi possível criar um novo usuário!"),
                HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ReplyMessage> edit(@RequestBody User user, @PathVariable(value = "id") int id) {
        user.setId(id);
        boolean update = db.update(user);
        return new ResponseEntity<>(
                new ReplyMessage(update ? "Update OK!" : "Update FAIL!"),
                HttpStatus.OK);
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
