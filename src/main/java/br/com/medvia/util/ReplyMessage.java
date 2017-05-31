package br.com.medvia.util;

/**
 *
 * @author Willian Kirschner willkev@gmail.com
 */
public class ReplyMessage {

    private final String message;
    private final String warnning;

    public ReplyMessage(String message) {
        this(message, "");
    }

    public ReplyMessage(String message, String warnning) {
        this.message = message;
        this.warnning = warnning;
    }

    public String getWarnning() {
        return warnning;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ReplyMessage{" + "message=" + message + '}';
    }

}
