/*
-retornar nomes dos users dos tickets
-pegar do cokie o userID para gravar no ticket em "aberto por"
-pegar do cokie o userID para gravar na Nota, quem criou ela

*/

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
public class EquipmentController {

    private static final String METHOD_EDIT = "/{id}";
    private static final String METHOD_DROP = "/drop";
    private static final String METHOD_CREATEFAKES = "/createfakes";

    public EquipmentController() {
        System.out.println(EquipmentController.class.getSimpleName() + " OK!");
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Equipment>> list() {
        List<Equipment> selected = DBManager.getInstance().getDbEquipment().selectAll();
        
        return new ResponseEntity<>(selected, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Map<String, Object>>> list(@RequestParam(value = "fields", required = true) String fields) {
        List<Map<String, Object>> selectAll = DBManager.getInstance().getDbTicket().executeQuery("");
        
        return new ResponseEntity<>(selectAll, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ReplyMessage> create(@RequestBody Equipment equipment) {
        System.out.println(equipment.toString());
        boolean insert = DBManager.getInstance().getDbEquipment().insert(equipment);
        return new ResponseEntity<>(
                new ReplyMessage(insert ? "Criou novo equipamento com sucesso!" : "Não foi possível criar um novo equipamento!"),
                HttpStatus.OK);
    }

    @RequestMapping(path = METHOD_EDIT, method = RequestMethod.PUT)
    public ResponseEntity<ReplyMessage> edit(@RequestBody Equipment equipment,
            @RequestParam(value = "id", defaultValue = "-1") int id) {
        System.out.println("ID = " + id);
        if (id < 0) {
            return new ResponseEntity<>(new ReplyMessage("ID inválido!"), HttpStatus.OK);
        }
        equipment.setId(id);
        boolean update = DBManager.getInstance().getDbEquipment().update(equipment);
        return new ResponseEntity<>(
                new ReplyMessage(update ? "Update OK!" : "Update FAIL!"),
                HttpStatus.OK);
    }

    @RequestMapping(METHOD_DROP)
    public ResponseEntity<ReplyMessage> drop() {
        boolean dropAndCreateTable = DBManager.getInstance().getDbEquipment().dropAndCreateTable();
        return new ResponseEntity<>(
                new ReplyMessage(dropAndCreateTable ? "Todos equipamentos deletados com sucesso!" : "Erro ao deletar todos equipamentos!"),
                HttpStatus.OK);
    }

    @RequestMapping(METHOD_CREATEFAKES)
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
