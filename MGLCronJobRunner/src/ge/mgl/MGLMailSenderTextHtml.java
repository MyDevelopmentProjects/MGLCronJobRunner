package ge.mgl;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MGLMailSenderTextHtml {

    private static final String PORT = "25";
    private static final String HOST = "185.163.200.14";
    private static final String USERNAME = "reports@mgldev.ge";
    private static final String PASSWORD = "1ewnJMzJVe";
    private static final String FROM = "backups@app.salonpro.ge";

    private MGLMailSenderTextHtml() {
    }

    private Properties initProperties() {
        Properties mailProperties = new Properties();
        mailProperties.setProperty("mail.smtp.host", HOST);
        mailProperties.setProperty("mail.smtp.user", USERNAME);
        mailProperties.setProperty("mail.smtp.password", PASSWORD);
        mailProperties.setProperty("mail.smtp.auth", "true");
        return mailProperties;
    }

    private Session initSession(Properties props) {
        return Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });
    }

    static void sendEmail(String[] recipients, String subject, String body) {

        MGLMailSenderTextHtml javaMail = new MGLMailSenderTextHtml();

        Properties props = javaMail.initProperties();
        Session session = javaMail.initSession(props);

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(FROM));

            Address[] toAddresses = new Address[recipients.length];
            for (int i = 0; i < recipients.length; i++) {
                toAddresses[i] = new InternetAddress(recipients[i]);
            }

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO, toAddresses);

            // Set Subject: header field
            message.setSubject(subject);

            message.setContent(body, "text/html; charset=utf-8");
            // Send message
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
