package br.com.medvia;

import br.com.medvia.util.ReplyMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Willian Kirschner willkev@gmail.com
 */
class AbstractController {

    static final String ID_NOT_FOUND = "ID não encontrado!";
    static final String FIELD_MANDATORY = "Campo obrigatório não informado! ";

    static final String PATH_FAKES = "/createfakes";

    final String controllerName;

    AbstractController(String controllerName) {
        System.out.println("Started " + controllerName);
        this.controllerName = controllerName;
    }

    public ResponseEntity<ReplyMessage> fakesCreated(int count) {
        return returnOK(count + " fakes foram criados com sucesso! " + controllerName);
    }

    ResponseEntity<ReplyMessage> returnOK(String msg) {
        return new ResponseEntity<>(new ReplyMessage(msg), HttpStatus.OK);
    }

    ResponseEntity<ReplyMessage> returnFieldMandatory(String fieldName) {
        return returnBadRequest(FIELD_MANDATORY + fieldName);
    }

    ResponseEntity<ReplyMessage> returnBadRequest(String msg) {
        return new ResponseEntity<>(new ReplyMessage(msg), HttpStatus.BAD_REQUEST);
    }

    boolean isValueOK(String value) {
        if (value == null) {
            return false;
        }
        return !value.isEmpty();
    }

    boolean isValueOK(Integer value) {
        return isValueOK(value, 0, Integer.MAX_VALUE);
    }

    boolean isValueOK(Integer value, int min, int max) {
        if (value == null) {
            return false;
        }
        return value >= min && value <= max;
    }

    boolean isValueOK(Double value) {
        if (value == null) {
            return false;
        }
        return value >= 0.0;
    }

}
