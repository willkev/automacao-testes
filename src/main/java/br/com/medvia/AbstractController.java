package br.com.medvia;

import br.com.medvia.util.ReplyMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    Integer verifyUser(String userIdStr) {
        Integer userId = null;
        try {
            userId = Integer.parseInt(userIdStr);
        } catch (Exception e) {
            throw new UnsupportedOperationException("No permission!");
        }
        if (userId == null || userId <= 0) {
            throw new UnsupportedOperationException("No permission!");
        }
        return userId;
    }

    ResponseEntity<ReplyMessage> fakesCreated(int count) {
        return returnOK(count + " fakes foram criados com sucesso! " + controllerName);
    }

    ResponseEntity<ReplyMessage> returnFieldMandatory(String fieldName) {
        return returnFail(FIELD_MANDATORY + fieldName);
    }

    ResponseEntity<ReplyMessage> returnMsgUpdate(boolean update) {
        return returnMsg(update, "Update OK!", "Update FAIL!");
    }

    ResponseEntity<ReplyMessage> returnMsgDelete(boolean delete) {
        return returnMsg(delete, "Delete OK!", "Delete FAIL!");
    }

    ResponseEntity<ReplyMessage> returnMsg(boolean executed, String msgOK, String msgFail) {
        if (executed) {
            return returnOK(msgOK);
        }
        return returnFail(msgFail);
    }

    ResponseEntity<ReplyMessage> returnFail(String msg) {
        return new ResponseEntity<>(new ReplyMessage(msg), HttpStatus.BAD_REQUEST);
    }

    ResponseEntity<ReplyMessage> returnOK(String msg) {
        return new ResponseEntity<>(new ReplyMessage(msg), HttpStatus.OK);
    }

    ResponseEntity<InputStreamResource> downloadFile(File file, MediaType mediaType) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        if (MediaType.APPLICATION_PDF == mediaType) {
            headers.add("Content-Disposition", "attachment; filename=pdf.pdf");
        }
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(mediaType)
                .body(new InputStreamResource(new FileInputStream(file)));
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
