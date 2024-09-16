package com.notifyme.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

class SMSNotifier implements Notifier {

    private static final Logger logger = LoggerFactory.getLogger(SMSNotifier.class);
    public static final String ACCOUNT_SID = "AC26*********************19e246";
    public static final String AUTH_TOKEN = "f201a*********************4b678c";

    @Override
    public void send(String message) {
        try {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

            Message sms = Message.creator(
                    new PhoneNumber("+91********8"),  // Replace with recipient phone number
                    new PhoneNumber("+1*********5"),  // Replace with your Twilio phone number
                    message
            ).create();

            logger.info("SMS sent successfully: {}", sms.getSid());
        } catch (Exception e) {
            logger.error("Failed to send SMS: ", e);
        }
    }
}
