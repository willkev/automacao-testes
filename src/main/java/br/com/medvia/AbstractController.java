package br.com.medvia;

import br.com.medvia.util.ReplyMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Willian
 */
class AbstractController {

    static final String PATH_DROP = "/drop";
    static final String PATH_FAKES = "/createfakes";

    AbstractController() {
    }

    ResponseEntity<ReplyMessage> returnOK(String msg) {
        return new ResponseEntity<>(new ReplyMessage(msg), HttpStatus.OK);
    }

    boolean isValueOK(String value) {
        if (value == null) {
            return false;
        }
        return !value.isEmpty();
    }

    boolean isValueOK(Integer value) {
        if (value == null) {
            return false;
        }
        return value >= 0;
    }

    boolean isValueOK(Double value) {
        if (value == null) {
            return false;
        }
        return value >= 0.0;
    }

}
