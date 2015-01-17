package com.uplan.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
/**
 * Created by tanvimehta on 15-01-16.
 */
public class EmailSender {
    private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);

    public static void sendEmail(String email, String subject, String body) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("camplanuoft@gmail.com", "stsgzmhl");
                    }
                });

        try {

            logger.info("Sending email to:[" + email + "] with Subject [" + subject + "]");
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("camplanuoft@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(subject);
            message.setContent(body,  "text/html");

            Transport.send(message);

            logger.info("Email sent to: "+ email);

        } catch (Exception e) {
            logger.error("Error sending email to:" + email, e);
            throw new RuntimeException(e);
        }
    }
}

