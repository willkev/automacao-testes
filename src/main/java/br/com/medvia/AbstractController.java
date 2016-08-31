package br.com.medvia;

import br.com.medvia.util.ReplyMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Willian
 */
class AbstractController {

    public AbstractController() {
    }

    ResponseEntity<ReplyMessage> returnOK(String msg) {
        return new ResponseEntity<>(new ReplyMessage(msg), HttpStatus.OK);
    }

}
