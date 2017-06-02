package br.com.medvia.mail;

import br.com.medvia.resources.Equipment;
import br.com.medvia.resources.User;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Willian Kirschner willkev@gmail.com
 */
public class EmailSender {

    public static boolean TEST_RUNNING = false;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);

    public enum Template {
        createTicket, closeTicket, deleteTicket
    }

    private static final String CREATE_TICKET_SUBJECT = "Novo Chamado Aberto \"%s\"";
    private static final String CREATE_TICKET_CONTENT = "Um novo chamando foi aberto!<br><br>"
            + "<b>Aberto por:</b> %s<br>"
            + "<b>Responsável:</b> %s<br>"
            + "<b>Previsão de conserto:</b> %s<br>"
            + "<b>Equipamento:</b> %s<br>"
            + "<br><br><br><br><i><small>Esta é uma mensagem automática.</small></i>";

    public boolean sendCreateTicket(User userTo, User responsable, String titleTicket, String predictionDate, Equipment equipment) {
        String subject = String.format(CREATE_TICKET_SUBJECT, titleTicket);
        String content = String.format(CREATE_TICKET_CONTENT,
                userTo.getName(),
                responsable.getName(),
                predictionDate == null || predictionDate.isEmpty() ? "Sem previsão." : predictionDate,
                equipment == null ? "Equipamento sem nome." : equipment.getName());
        return send(userTo.getEmail(), subject, content);
    }

    public boolean send(String to, String subject, String content) {
        // Se estiver rodando testes
        if (TEST_RUNNING) {
            return true;
        }
        Properties props = System.getProperties();
        final String host = "smtp.gmail.com";
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.ssl.trust", host);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.debug", "true");
        /*
         1. Login your google account
         2. In google search, search “Application-specific password”
         3. Enter a label for your reference and select “generate password”
         */
        //Password Generated using above steps
        final String password = "ssptvdjolpqucepk";
        final String username = "medvia.suporte";
        final String senderEmail = username + "@gmail.com";
        try {
            Session session = Session.getDefaultInstance(props,
                    new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message msg = new MimeMessage(session);
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setFrom(new InternetAddress(senderEmail));
            msg.setSubject(subject);
            //msg.setText(content);
            msg.setContent(content, "text/html; charset=utf-8");
            msg.setSentDate(new Date());
            Transport.send(msg);

            LOGGER.info("Successfully sent emai!");
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        LOGGER.error("Fail to send emai!");
        return false;
    }
}
