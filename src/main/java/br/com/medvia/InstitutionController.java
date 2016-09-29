package br.com.medvia;

import br.com.medvia.db.WkDB;
import br.com.medvia.resources.Institution;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Willian Kirschner willkev@gmail.com
 */
@RestController
@RequestMapping("/api/institutions")
@CrossOrigin
public class InstitutionController extends AbstractController {

    private final EquipmentController equipmentController = new EquipmentController();

    private final WkDB<Institution> db;

    public InstitutionController() {
        super(InstitutionController.class.getSimpleName());
        db = new WkDB<>(Institution.class);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Institution>> list() {
        List<Institution> selectAll = db.selectAll();
        return new ResponseEntity<>(selectAll, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ReplyMessage> create(@RequestBody Institution institution) {
        boolean insert = db.insert(institution);
        return new ResponseEntity<>(
                new ReplyMessage(insert ? "Criou nova instituição com sucesso!" : "Não foi possível criar uma nova instituição!"),
                HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ReplyMessage> edit(@RequestBody Institution institution, @PathVariable(value = "id") int id) {
        institution.setId(id);
        boolean update = db.update(institution);
        return new ResponseEntity<>(
                new ReplyMessage(update ? "Update OK!" : "Update FAIL!"),
                HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}/equipments", method = RequestMethod.GET)
    public ResponseEntity<List<?>> listEquipmentsByInstitution(@PathVariable(value = "id") int id,
            @RequestParam(value = "fields", defaultValue = "") String fields) {
        return equipmentController.list(fields, id);
    }

    @RequestMapping(PATH_FAKES)
    public ResponseEntity<ReplyMessage> createfakes() {
        List<Institution> created = Fakes.createInstitutions();
        created.stream().forEach((element) -> {
            create(element);
        });
        return fakesCreated(created.size());
    }

}
