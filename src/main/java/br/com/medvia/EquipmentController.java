package br.com.medvia;

import br.com.medvia.db.WkDB;
import br.com.medvia.resources.Equipment;
import br.com.medvia.resources.Institution;
import br.com.medvia.resources.TypeEquipment;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Willian Kirschner willkev@gmail.com
 */
@RestController
@RequestMapping("/api/equipments")
@CrossOrigin
public class EquipmentController extends AbstractController {

    public static final String QUERY_LIST = "select e.id, e.name equipment, i.description institution, te.description typeEquipment, case when e.active = 0 then 'i' else 'a' end status, '0' state from equipment e, Institution i, TypeEquipment te where e.institutionId = i.id and te.id = e.typeEquipmentId";

    private final WkDB<Equipment> db;
    private final WkDB<TypeEquipment> dbTE;

    public EquipmentController() {
        super(EquipmentController.class.getSimpleName());
        db = new WkDB<>(Equipment.class);
        dbTE = new WkDB<>(TypeEquipment.class);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<?>> list(@RequestParam(value = "fields", defaultValue = "") String fields) {
        if (fields == null || fields.isEmpty()) {
            List<Map<String, Object>> list = db.executeQuery(QUERY_LIST);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        return list(fields, null);
    }

    public ResponseEntity<List<?>> list(String fields, Integer institutionID) {
        String filterByInstitution = null;
        if (institutionID != null) {
            filterByInstitution = "where institutionId = " + institutionID;
        }
        if (fields == null || fields.isEmpty()) {
            List<Equipment> selected = db.selectAll(filterByInstitution);
            return new ResponseEntity<>(selected, HttpStatus.OK);
        }
        List<Map<String, Object>> selectAll = db.executeQuery(fields, filterByInstitution);
        return new ResponseEntity<>(selectAll, HttpStatus.OK);
    }

    @RequestMapping(path = "/typesequipment", method = RequestMethod.GET)
    public ResponseEntity<List<TypeEquipment>> listTypeEquipment() {
        List<TypeEquipment> list = dbTE.selectAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Equipment> get(@PathVariable(value = "id") int id) {
        Equipment selectOne = db.selectById(id);
        return new ResponseEntity<>(selectOne, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ReplyMessage> create(@RequestBody Equipment equipment) {
        equipment.setActive(true);
        boolean insert = db.insert(equipment);
        if (insert) {
            return returnOK("Criou novo equipamento com sucesso!");
        }
        return returnBadRequest("Não foi possível criar um novo equipamento!");
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ReplyMessage> edit(@RequestBody Equipment equipment, @PathVariable(value = "id") int id) {
        equipment.setId(id);
        boolean update = db.update(equipment);
        if (update) {
            return returnOK("Update OK!");
        }
        return returnBadRequest("Update Fail!");
    }

    @RequestMapping(PATH_FAKES)
    public ResponseEntity<ReplyMessage> createfakes() {
        List<Institution> institutions = new WkDB<>(Institution.class).selectAll();
        // se ainda não existir nenhum 
        if (institutions.isEmpty()) {
            return returnOK("Nenhuma instituição ainda foi criada!");
        }
        List<TypeEquipment> typesEquipment = new WkDB<>(TypeEquipment.class).selectAll();
        // se ainda não existir nenhum 
        if (typesEquipment.isEmpty()) {
            return returnOK("Nenhum tipo de equipamento ainda foi criado!");
        }
        List<Equipment> created = Fakes.createEquipments(institutions, typesEquipment);
        created.stream().forEach((element) -> {
            create(element);
        });
        return fakesCreated(created.size());
    }

}
