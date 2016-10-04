package br.com.medvia;

import br.com.medvia.db.WkDB;
import br.com.medvia.resources.Equipment;
import br.com.medvia.resources.QualityControl;
import br.com.medvia.resources.User;
import br.com.medvia.util.Fakes;
import br.com.medvia.util.ReplyMessage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Willian Kirschner willkev@gmail.com
 */
@RestController
@RequestMapping("/api/quality-control")
@CrossOrigin
public class QualityControlController extends AbstractController {

    public static final String QUERY_LIST = "select q.id, e.name equipment, i.description institution, q.test, q.dateExecution, q.dateValidity, q.compliance, q.hasPDF from QualityControl q, Equipment e, Institution i where q.equipmentId = e.id and e.institutionId = i.id";
    public static final String QUERY_LIST_ID = "select q.*, e.institutionId from QualityControl q, Equipment e where q.equipmentId = e.id and q.id = ";

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
    public ResponseEntity<?> get(@PathVariable(value = "id") int id) {
        List<Map<String, Object>> select = db.executeQuery(QUERY_LIST_ID + id);
        return new ResponseEntity<>(select.get(0), HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}/pdf", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<?> getPDF(@PathVariable(value = "id") int id) throws IOException {
        File pdf = new File(getClass().getClassLoader().getResource("FakePDF.pdf").getFile());
        return downloadFile(pdf, MediaType.APPLICATION_PDF);
    }

    @RequestMapping(path = "/{id}/pdf", method = RequestMethod.POST)
    public ResponseEntity<?> getPDF(@PathVariable(value = "id") int id, @RequestParam("file") MultipartFile pdf) throws IOException {
        return returnOK("OK PDF size:" + pdf.getSize());
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
        return returnMsg(insert, "Criou novo controle de qualidade com sucesso!", "Não foi possível criar um controle de qualidade!");
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ReplyMessage> edit(@PathVariable(value = "id") int id, @RequestBody QualityControl qualityControl) {
        qualityControl.setId(id);
        boolean update = db.update(qualityControl);
        return returnMsgUpdate(update);
    }

    @RequestMapping(PATH_FAKES)
    public ResponseEntity<ReplyMessage> createFakes() {
        List<User> users = new WkDB<>(User.class).selectAll();
        // se ainda não existir nenhum 
        if (users.isEmpty()) {
            return returnFail("Nenhum usuário ainda foi criado!");
        }
        List<Equipment> equipments = new WkDB<>(Equipment.class).selectAll();
        // se ainda não existir nenhum 
        if (equipments.isEmpty()) {
            return returnFail("Nenhum equipamento ainda foi criado!");
        }
        List<QualityControl> created = Fakes.createQualityControl(users, equipments);
        created.stream().forEach((element) -> {
            create(element);
        });
        return fakesCreated(created.size());
    }

}
