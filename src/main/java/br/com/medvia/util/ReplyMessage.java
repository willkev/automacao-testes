package br.com.medvia.util;

/**
 *
 * @author willian.kirschner
 */
public class ReplyMessage {

    private final String message;

    public ReplyMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ReplyMessage{" + "message=" + message + '}';
    }

}
