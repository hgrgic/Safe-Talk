package io.safe.talk.controller.mail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMail extends MailAuth {
    private Session session;

    public void verifyUser(Properties properties, String user, String password){
        this.session = super.authenthicateUser(properties, user, password);
    }

    public void sendEmail(String recipients, String subject, String messageText){
        try {
            Message message = new MimeMessage(session);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
            message.setSubject(subject);
            message.setText(messageText);

            Transport.send(message);
            System.out.println("Mail Sent");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
