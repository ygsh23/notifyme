package com.notifyme.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

class EmailNotifier implements Notifier {
    private static final Logger logger = LoggerFactory.getLogger(EmailNotifier.class);

    @Override
    public void send(String message) {
        String to = "y************@protonmail.com";
        String from = "y*************@outlook.com";
        String host = "smtp.office365.com";
        String port = "587";

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", port);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("y*********@outlook.com", "**********");
            }
        });

        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mimeMessage.setSubject("Test Email Notification");
            mimeMessage.setText(message);

            Transport.send(mimeMessage);
            logger.info("Email sent successfully: {}", message);
        } catch (MessagingException e) {
            logger.error(e.getMessage());
        }
    }
}
