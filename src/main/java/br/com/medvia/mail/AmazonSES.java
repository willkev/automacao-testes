//package br.com.medvia.mail;
//
//import java.util.Properties;
//import javax.mail.Message;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//
///**
// *
// * @author Willian Kirschner willkev@gmail.com
// */
//public class AmazonSES {
//
//    static final String FROM = "willkev@gmail.com";   // Replace with your "From" address. This address must be verified.
//    static final String TO = "willkev@gmail.com";  // Replace with a "To" address. If your account is still in the 
//    // sandbox, this address must be verified.
//
//    static final String BODY = "This email was sent through the Amazon SES SMTP interface by using Java.";
//    static final String SUBJECT = "Amazon SES test (SMTP interface accessed using Java)";
//
//    // Supply your SMTP credentials below. Note that your SMTP credentials are different from your AWS credentials.
//    static final String SMTP_USERNAME = "AKIAJ7PTCUJJP3BUSL3Q";  // Replace with your SMTP username.
//    static final String SMTP_PASSWORD = "AmbPlxEWJH0GfEmIiHkm8xwuDMTJDH5WWs/wxjVT1Vbj";  // Replace with your SMTP password.
//
//    // Amazon SES SMTP host name. This example uses the US West (Oregon) region.
//    static final String HOST = "email-smtp.us-west-2.amazonaws.com";//"email-smtp.us-east-1.amazonaws.com";
//
//    // The port you will connect to on the Amazon SES SMTP endpoint. We are choosing port 25 because we will use
//    // STARTTLS to encrypt the connection.
//    static final int PORT = 587;
//
//    public void sendEmail() throws Exception {
//
//        // Create a Properties object to contain connection configuration information.
//        Properties props = System.getProperties();
//        props.put("mail.transport.protocol", "smtps");
//        props.put("mail.smtp.port", PORT);
//
//        // Set properties indicating that we want to use STARTTLS to encrypt the connection.
//        // The SMTP session will begin on an unencrypted connection, and then the client
//        // will issue a STARTTLS command to upgrade to an encrypted connection.
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.starttls.required", "true");
//
//        // Create a Session object to represent a mail session with the specified properties. 
//        Session session = Session.getDefaultInstance(props);
//
//        // Create a message with the specified information. 
//        MimeMessage msg = new MimeMessage(session);
//        msg.setFrom(new InternetAddress(FROM));
//        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
//        msg.setSubject(SUBJECT);
//        msg.setContent(BODY, "text/plain");
//
//        // Create a transport.        
//        Transport transport = session.getTransport();
//
//        // Send the message.
//        try {
//            System.out.println("Attempting to send an email through the Amazon SES SMTP interface...");
//
//            // Connect to Amazon SES using the SMTP username and password you specified above.
//            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
//
//            // Send the email.
//            transport.sendMessage(msg, msg.getAllRecipients());
//            System.out.println("Email sent!");
//        } catch (Exception ex) {
//            System.out.println("The email was not sent.");
//            System.out.println("Error message: " + ex.getMessage());
//        } finally {
//            // Close and terminate the connection.
//            transport.close();
//        }
//    }
//}
