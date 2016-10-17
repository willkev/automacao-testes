package br.com.medvia.mail;

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
 * @author Willian
 */
public class EmailSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);

    public boolean send(String to, String subject, String content) {
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
         4. Copy that password and paste it in code at “//password here” line. 
         */
        final String password = "npdddzkxyrvzybhw"; //Password Generated using above steps
        final String username = "willkev";
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
            msg.setText(content);
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
