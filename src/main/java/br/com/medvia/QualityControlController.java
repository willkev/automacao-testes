package br.com.medvia;

import br.com.medvia.db.WkDB;
import br.com.medvia.resources.Equipment;
import br.com.medvia.resources.QualityControl;
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
@RequestMapping("/api/quality-control")
@CrossOrigin
public class QualityControlController extends AbstractController {

    public static final String QUERY_LIST = "select q.id, e.name equipment, i.description institution, q.test, q.dateExecution, q.dateValidity, q.compliance from QualityControl q, Equipment e, Institution i where q.equipmentId = e.id and e.institutionId = i.id";

    private final WkDB<QualityControl> db;

    public QualityControlController() {
        super(QualityControlController.class.getSimpleName());
        db = new WkDB<>(QualityControl.class);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> list() {
        List<Map<String, Object>> selectAll = db.executeQuery(QUERY_LIST);
        return new ResponseEntity<>(selectAll, HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<QualityControl> get(@PathVariable(value = "id") int id) {
        return new ResponseEntity<>(db.selectById(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ReplyMessage> create(@RequestBody QualityControl qualityControl) {
        // valida campos obrigatórios
        if (!isValueOK(qualityControl.getUserId(), 1, Integer.MAX_VALUE)) {
            return returnFieldMandatory("Usuário Criador");
        }
        if (!isValueOK(qualityControl.getTest())) {
            return returnFieldMandatory("Descrição");
        }
        boolean insert = db.insert(qualityControl);
        if (insert) {
            return returnOK("Criou novo controle de qualidade com sucesso!");
        }
        return returnBadRequest("Não foi possível criar um controle de qualidade!");
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ReplyMessage> edit(@PathVariable(value = "id") int id, @RequestBody QualityControl qualityControl) {
        qualityControl.setId(id);
        boolean update = db.update(qualityControl);
        if (update) {
            return returnOK("Update OK!");
        }
        return returnBadRequest("Update Fail!");
    }

    @RequestMapping(PATH_FAKES)
    public ResponseEntity<ReplyMessage> createFakes() {
        List<User> users = new WkDB<>(User.class).selectAll();
        // se ainda não existir nenhum 
        if (users.isEmpty()) {
            return returnBadRequest("Nenhum usuário ainda foi criado!");
        }
        List<Equipment> equipments = new WkDB<>(Equipment.class).selectAll();
        // se ainda não existir nenhum 
        if (equipments.isEmpty()) {
            return returnBadRequest("Nenhum equipamento ainda foi criado!");
        }
        List<QualityControl> created = Fakes.createQualityControl(users, equipments);
        created.stream().forEach((element) -> {
            create(element);
        });
        return fakesCreated(created.size());
    }

}
