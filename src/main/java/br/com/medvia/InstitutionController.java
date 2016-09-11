package br.com.medvia;

import br.com.medvia.db.DBManager;
import br.com.medvia.resources.Institution;
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
@RequestMapping("/api/institutions")
@CrossOrigin
public class InstitutionController extends AbstractController {

    public InstitutionController() {
        System.out.println(InstitutionController.class.getSimpleName() + " OK!");
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Institution>> list() {
        List<Institution> selectAll = DBManager.getInstance().getDbInstitution().selectAll();
        return new ResponseEntity<>(selectAll, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ReplyMessage> create(@RequestBody Institution institution) {
        boolean insert = DBManager.getInstance().getDbInstitution().insert(institution);
        return new ResponseEntity<>(
                new ReplyMessage(insert ? "Criou nova instituição com sucesso!" : "Não foi possível criar uma nova instituição!"),
                HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ReplyMessage> edit(@RequestBody Institution institution, @RequestParam(value = "id") int id) {
        institution.setId(id);
        boolean update = DBManager.getInstance().getDbInstitution().update(institution);
        return new ResponseEntity<>(
                new ReplyMessage(update ? "Update OK!" : "Update FAIL!"),
                HttpStatus.OK);
    }

    @RequestMapping(PATH_DROP)
    public ResponseEntity<ReplyMessage> drop() {
        DBManager.getInstance().getDbInstitution().dropAndCreateTable();
        return new ResponseEntity<>(
                new ReplyMessage("Todas instituições foram deletados com sucesso!"),
                HttpStatus.OK);
    }

    @RequestMapping(PATH_FAKES)
    public ResponseEntity<ReplyMessage> createfakes() {
        List<Institution> created = Fakes.createInstitutions();
        created.stream().forEach((element) -> {
            create(element);
        });
        return new ResponseEntity<>(
                new ReplyMessage(created.size() + " fakes foram criados com sucesso!"),
                HttpStatus.OK);
    }

}
