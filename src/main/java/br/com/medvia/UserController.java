package br.com.medvia;

import br.com.medvia.db.WkDB;
import br.com.medvia.db.WkDB.Where;
import br.com.medvia.resources.User;
import br.com.medvia.resources.UserFull;
import br.com.medvia.util.Fakes;
import br.com.medvia.util.ReplyMessage;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/api/users")
@CrossOrigin
public class UserController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public static final String QUERY_LIST = "select id, name, institutionsList from User";

    private final WkDB<User> db;

    public UserController() {
        super(UserController.class.getSimpleName());
        db = new WkDB<>(User.class);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> list(@RequestHeader(value = "userId", required = false) String token) {
        Integer userId = verifyUser(token);
        User currentUser = db.selectById(userId);
        if (currentUser == null) {
            return returnFail("Usuário não encontrado");
        }
        // valida conforme o nível de permissão do User
        if (currentUser.getPermissionLevel() >= 2) {
            return returnFail("Usuário sem permissão para visualizar outros usuários!");
        }
        // Apenas Super Admin podem visualizar todos users
        List<Map<String, Object>> selectAll = db.executeQuery(
                QUERY_LIST + (currentUser.getPermissionLevel() == 0 ? "" : " where permissionLevel >= 1")
        );
        //
        // TODO: o filtro pode ser melhorado e feito na própria query
        //
        // se for um admin de instituições
        if (currentUser.getPermissionLevel() == 1) {

            ArrayList<String> listId = new ArrayList<>();
            // deve exibir só os usuário das instituições que o User logado tbm pertence
            try {
                String[] split = currentUser.getInstitutionsList().split(",");
                for (String id : split) {
                    listId.add(id);
                }
            } catch (Exception e) {
                log.error("Não foi possível verificar as instituições do User!", e);
            }
            // retorna apenas os filtrados
            selectAll = selectAll.stream()
                    .filter((user) -> {
                        String institutionsList = (String) user.get("institutionsList");
                        // se o user pertence a mesma instituição do user logado
                        if (listId.stream().anyMatch((id) -> (institutionsList.contains(id)))) {
                            return true;
                        }
                        return false;
                    })
                    .collect(Collectors.toList());
        }
        return new ResponseEntity<>(selectAll, HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@RequestHeader(value = "userId", required = false) String token,
            @PathVariable(value = "id") int id) {
        User currentUser = db.selectById(id);;
        if (currentUser == null) {
            return returnFail("Usuário não encontrado");
        }
        String userJson = currentUser.meToJson();
        UserFull userFull = new Gson().fromJson(userJson, UserFull.class);
        try {
            String[] ids = currentUser.getInstitutionsList().split(",");
            Integer[] institutions = new Integer[ids.length];
            for (int i = 0; i < ids.length; i++) {
                institutions[i] = Integer.valueOf(ids[i]);
            }
            userFull.setInstitutions(institutions);
        } catch (Exception e) {
            log.error("Não foi possível verificar as instituições do User!", e);
        }
        userFull.setPassword("");
        userFull.setInstitutionsList("");
        return new ResponseEntity<>(userFull, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ReplyMessage> create(@RequestHeader(value = "userId", required = false, defaultValue = "") String token,
            @RequestBody UserFull userFull) {
        // token especial
        if (!"9e1953c6-7613-4741-8327-8136a9cf2b87".equals(token)) {
            Integer userId = verifyUser(token);
            User currentUser = db.selectById(userId);
            // valida se tem permissão de criar user
            if (currentUser == null || currentUser.getPermissionLevel() >= 2) {
                return returnFail("Apenas Administradores podem criar Usuários!");
            }
            if (userFull.getPermissionLevel() == null) {
                return returnFail("PermissionLevel inválido!");
            }
            if (userFull.getPassword() == null || userFull.getPassword().length() < 6) {
                return returnFail("Password inválido!");
            }
            // se o nível de permissão do user requisitado é maior que o user corrente
            if (currentUser.getPermissionLevel() > userFull.getPermissionLevel()) {
                return returnFail("Nível de permissão não permite esta ação!");
            }
        }
        //
        // BUG!
        // TODO: Se User do tipo "1 - Admin (por Instituição)" tiver acesso apenas a 1 instituição e 
        //       tentar criar um user para outra, vai funcionar
        //
        boolean insert = false;
        if (userFull.getEmail() != null && userFull.getEmail().matches("\\S{3,}@\\w{3,10}.\\S{2,}")) {
            User user = userFull;
            user.convertInstitutionsList(userFull.getInstitutions());
            // se não for um nível de permissão permitido, seta como "User Normal"
            if (user.getPermissionLevel() < 0 || user.getPermissionLevel() > 2) {
                user.setPermissionLevel(2);
            }
            insert = db.insert(user);
        }
        return returnMsg(insert, "Criou novo usuário com sucesso!", "Não foi possível criar um novo usuário!");
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ReplyMessage> edit(@RequestHeader(value = "userId", required = false) String token,
            @RequestBody UserFull userFull, @PathVariable(value = "id") int id) {
        User user = userFull;
        user.convertInstitutionsList(userFull.getInstitutions());
        user.setId(id);
        boolean update = db.update(user);
        return returnMsgUpdate(update);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ReplyMessage> delete(@RequestHeader(value = "userId", required = false) String token,
            @PathVariable(value = "id") int id) {
        boolean deleteById = db.deleteById(id);
        return returnMsgDelete(deleteById);
    }

    // required = false, para não expor a assinatura do método de login
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public User login(@RequestBody(required = false) User user) {
        if (user == null || !isNotNullNotEmpty(user.getEmail()) || !isNotNullNotEmpty(user.getPassword())) {
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
        get.setInstitutionsList(null);
        return get;
    }

    @RequestMapping(PATH_FAKES)
    public ResponseEntity<ReplyMessage> createFakes() {
        List<UserFull> createUsers = Fakes.createUsers();
        createUsers.stream().forEach((element) -> {
            // Cria o 1º user ignorando a permissão, pois para criar um User é precisa da permissão de outro
            // Passa um Token específico para isto!
            if (element.getId() == -666) {
                create("9e1953c6-7613-4741-8327-8136a9cf2b87", element);
            } else {
                create("1", element);
            }
        });
        return fakesCreated(createUsers.size());
    }

}
