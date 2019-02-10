package io.safe.talk.test.mail;

import io.safe.talk.controller.mail.FetchMail;
import io.safe.talk.controller.mail.SendMail;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

import static junit.framework.TestCase.assertTrue;

public class MailTester {

    @Test
    @Ignore
    public void fetchGmailMail(){
        String host = "imap.gmail.com";// change accordingly
        String mailStoreType = "imaps";

        String username = "";// change accordingly
        String password = "";// change accordingly


        FetchMail mail = new FetchMail();
        mail.verifyUser(host, mailStoreType, username, password);
        Map mails = mail.fetchAllMail();

        assertTrue(!mails.isEmpty());
        assertTrue(mail.closeStoreConnection());
    }

    @Test
    @Ignore
    public void fetchYahooMail(){
        String host = "imap.mail.yahoo.com";
        String mailStoreType = "imaps";

        String username = "";// change accordingly
        String password = "";// change accordingly


        FetchMail mail = new FetchMail();
        mail.verifyUser(host, mailStoreType, username, password);
        Map mails = mail.fetchAllMail();

        assertTrue(!mails.isEmpty());
        assertTrue(mail.closeStoreConnection());
    }

    @Test
    @Ignore
    public void sendFromGmail(){
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        SendMail sm = new SendMail();
        sm.verifyUser(props,"", "");

        String recipients = "hgrgic@yahoo.com, hrvoje.grgic1996@gmail.com";
        String subject = "Testing Subject";
        String msg = "Dear Mail Crawler, \n\n No spam to my email, please!";

        sm.sendEmail(recipients, subject, msg);
    }

    @Test
    @Ignore
    //Does not work
    public void sendFromHotmail(){
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.live.com");


        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.socketFactory.port", "587");


        SendMail sm = new SendMail();
        sm.verifyUser(props,"hrvoje-grgic1996@hotmail.com", "");

        String recipients = "hgrgic@yahoo.com, hrvoje.grgic1996@gmail.com";
        String subject = "Testing Subject";
        String msg = "Dear Mail Crawler, \n\n No spam to my email, please!";

        sm.sendEmail(recipients, subject, msg);
    }


}
