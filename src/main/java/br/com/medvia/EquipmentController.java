package br.com.medvia;

import br.com.medvia.db.DBManager;
import br.com.medvia.resources.Equipment;
import br.com.medvia.util.Fakes;
import br.com.medvia.util.ReplyMessage;
import java.util.List;
import java.util.Map;
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
@RequestMapping("/api/equipments")
@CrossOrigin
public class EquipmentController extends AbstractController {

    private static final String LIST_BY_INST = "/api/institutions/{id}/equipments";

    public EquipmentController() {
        System.out.println(EquipmentController.class.getSimpleName() + " OK!");
    }

    @RequestMapping(path = LIST_BY_INST, method = RequestMethod.GET)
    public ResponseEntity<List<?>> listByInstitution(@RequestParam(value = "id") int id,
            @RequestParam(value = "fields", defaultValue = "") String fields) {
        return list0(fields, id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<?>> list(@RequestParam(value = "fields", defaultValue = "") String fields) {
        return list0(fields, null);
    }

    private ResponseEntity<List<?>> list0(String fields, Integer institutionID) {
        String filterByInstitution = null;
        if (institutionID != null) {
            filterByInstitution = "where institutionID = " + institutionID;
        }
        if (fields.isEmpty()) {
            List<Equipment> selected = DBManager.getInstance().getDbEquipment().selectAll(filterByInstitution);
            return new ResponseEntity<>(selected, HttpStatus.OK);
        }
        List<Map<String, Object>> selectAll = DBManager.getInstance().getDbEquipment().executeQuery(fields, filterByInstitution);
        return new ResponseEntity<>(selectAll, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ReplyMessage> create(@RequestBody Equipment equipment) {
        equipment.setActive(true);
        boolean insert = DBManager.getInstance().getDbEquipment().insert(equipment);
        return new ResponseEntity<>(
                new ReplyMessage(insert ? "Criou novo equipamento com sucesso!" : "Não foi possível criar um novo equipamento!"),
                HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ReplyMessage> edit(@RequestBody Equipment equipment, @RequestParam(value = "id") int id) {
        equipment.setId(id);
        boolean update = DBManager.getInstance().getDbEquipment().update(equipment);
        return new ResponseEntity<>(
                new ReplyMessage(update ? "Update OK!" : "Update FAIL!"),
                HttpStatus.OK);
    }

    @RequestMapping(PATH_DROP)
    public ResponseEntity<ReplyMessage> drop() {
        boolean dropAndCreateTable = DBManager.getInstance().getDbEquipment().dropAndCreateTable();
        return new ResponseEntity<>(
                new ReplyMessage(dropAndCreateTable ? "Todos equipamentos deletados com sucesso!" : "Erro ao deletar todos equipamentos!"),
                HttpStatus.OK);
    }

    @RequestMapping(PATH_FAKES)
    public ResponseEntity<ReplyMessage> createfakes() {
        List<Equipment> created = Fakes.createEquipments();
        created.stream().forEach((element) -> {
            create(element);
        });
        return new ResponseEntity<>(
                new ReplyMessage(created.size() + " fakes foram criados com sucesso!"),
                HttpStatus.OK);
    }

}
