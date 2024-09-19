package com.notifyme.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

class SMSNotifier implements Notifier {

    private static final Logger logger = LoggerFactory.getLogger(SMSNotifier.class);
    public static final String ACCOUNT_SID = "AC26******************************e246";
    public static final String AUTH_TOKEN = "f201*****************************78c";
    private final DataSource dataSource;

    @Inject
    public SMSNotifier(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void send(String message) {
        String phoneTo = "+91***********8";
        boolean delivered = false;

        try {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

            Message sms = Message.creator(new PhoneNumber(phoneTo),
                    new PhoneNumber("+1***********5"),
                    message).create();

            logger.info("SMS sent successfully: {}", sms.getSid());
            delivered = true;
        } catch (Exception e) {
            logger.error("Failed to send SMS: ", e);
        }

        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO sms_details (phone_to, message, delivered) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, phoneTo);
            ps.setString(2, message);
            ps.setBoolean(3, delivered);
            ps.executeUpdate();
        } catch (Exception e) {
            logger.error("Error logging SMS details: ", e);
        }
    }
}
