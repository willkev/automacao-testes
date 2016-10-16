package br.com.medvia.mail;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Willian
 */
public class Gmail {

    public void send() {
        String userName = "willkev";
        String from = "willkev@gmail.com";
        String pass = "novozero1234";
        String[] to = {from};
        String subject = "Email Teste";
        String body = "Corpo do email.";

        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");// 465
        
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        // props.setProperty("mail.transport.protocol", "smtp");
        props.put("mail.smtp.user", userName);
        props.put("mail.smtp.password", pass);

        Session session = Session.getDefaultInstance(props);
//        session.setDebug(true);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];
            // To get the array of addresses
            for (int i = 0; i < to.length; i++) {
                toAddress[i] = new InternetAddress(to[i]);
            }
            for (int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }
            message.setSubject(subject);
            message.setText(body);

            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());

            System.out.println("Successfully sent emai!");
            transport.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
