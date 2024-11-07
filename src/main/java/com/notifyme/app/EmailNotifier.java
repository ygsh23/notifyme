package com.notifyme.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

class EmailNotifier implements Notifier {
    private static final Logger logger = LoggerFactory.getLogger(EmailNotifier.class);
    private final DataSource dataSource;

    @Inject
    public EmailNotifier(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void send(String message) {
        String to = "yogeshh023@protonmail.com";
        String from = "yogeshh023@outlook.com";
        String host = "smtp.office365.com";
        String port = "587";

        String subject = "Test Email Notification";
        boolean delivered = false;

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", port);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("yogeshh023@outlook.com", "Good43!!");
            }
        });

        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);

            Transport.send(mimeMessage);
            logger.info("Email sent successfully: {}", message);
            delivered = true;
        } catch (MessagingException e) {
            logger.error(e.getMessage());
        }

        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO email_details (email_to, subject, body, delivered) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, to);
            ps.setString(2, subject);
            ps.setString(3, message);
            ps.setBoolean(4, delivered);
            ps.executeUpdate();
        } catch (Exception e) {
            logger.error("Error logging email details: ", e);
        }
    }
}
