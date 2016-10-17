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

/**
 *
 * @author Willian
 */
public class EmailSender {

    public boolean send(String emailTo, String subject, String content) {
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
        final String password = ""; //Password Generated using above steps
        final String username = "";
        final String senderEmail = username + "@gmail.com";
        try {
            Session session = Session.getDefaultInstance(props,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            Message msg = new MimeMessage(session);
            String[] to = {senderEmail, "?@gmail.com"};
            InternetAddress[] toAddress = new InternetAddress[to.length];
            for (int i = 0; i < to.length; i++) {
                toAddress[i] = new InternetAddress(to[i]);
            }
            for (int i = 0; i < toAddress.length; i++) {
                msg.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }
//            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("willkev@gmail.com,daia.brites@gmail.com", false));            
            msg.setFrom(new InternetAddress(senderEmail));
            msg.setSubject(subject);
            msg.setText(content);
            msg.setSentDate(new Date());
            Transport.send(msg);

            System.out.println("Successfully sent emai!");
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
