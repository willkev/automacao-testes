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
public class Gmail2 {

    public void send() {
        final String SSL = "javax.net.ssl.SSLSocketFactory";

        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");
        /*
         1. Login your google account
         2. In google search, search “Application-specific password”
         3. Enter a label for your reference and select “generate password”
         4. Copy that password and paste it in code at “//password here” line. 
         */
        final String password = "upesbpahruibeefu"; //Password Generated using above steps
        final String username = "medviamail"; // medviamail123456
        try {
            Session session = Session.getDefaultInstance(props,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            Message msg = new MimeMessage(session);
            String[] to = {"willkev@gmail.com", "daia.brites@gmail.com"};
            InternetAddress[] toAddress = new InternetAddress[to.length];
            for (int i = 0; i < to.length; i++) {
                toAddress[i] = new InternetAddress(to[i]);
            }
            for (int i = 0; i < toAddress.length; i++) {
                msg.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }
//            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("willkev@gmail.com,daia.brites@gmail.com", false));            
            msg.setFrom(new InternetAddress("medviamail@gmail.com"));
            msg.setSubject("Email Teste007");
            msg.setText("This email is a simples test!");
            msg.setSentDate(new Date());
            Transport.send(msg);

            System.out.println("Successfully sent emai!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
