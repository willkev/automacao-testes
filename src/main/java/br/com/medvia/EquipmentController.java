package br.com.medvia;

import br.com.medvia.db.WkDB;
import br.com.medvia.resources.Equipment;
import br.com.medvia.resources.TypeEquipment;
import br.com.medvia.resources.Institution;
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
 * @author Willian
 */
@RestController
@RequestMapping("/api/equipments")
@CrossOrigin
public class EquipmentController extends AbstractController {

    public static final String QUERY_LIST = "";

    private final WkDB<Equipment> db;

    public EquipmentController() {
        System.out.println(EquipmentController.class.getSimpleName() + " OK!");
        db = new WkDB<>(Equipment.class);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<?>> list(@RequestParam(value = "fields", defaultValue = "") String fields) {
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

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ReplyMessage> create(@RequestBody Equipment equipment) {
        equipment.setActive(true);
        boolean insert = db.insert(equipment);
        return new ResponseEntity<>(
                new ReplyMessage(insert ? "Criou novo equipamento com sucesso!" : "Não foi possível criar um novo equipamento!"),
                HttpStatus.OK);
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

    @RequestMapping(PATH_DROP)
    public ResponseEntity<ReplyMessage> drop() {
        boolean dropAndCreateTable = db.dropAndCreateTable();
        return new ResponseEntity<>(
                new ReplyMessage(dropAndCreateTable ? "Todos equipamentos deletados com sucesso!" : "Erro ao deletar todos equipamentos!"),
                HttpStatus.OK);
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
        return new ResponseEntity<>(
                new ReplyMessage(created.size() + " fakes foram criados com sucesso!"),
                HttpStatus.OK);
    }

}
