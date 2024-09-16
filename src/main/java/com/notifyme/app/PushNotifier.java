package com.notifyme.app;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;

class PushNotifier implements Notifier {

    private static final Logger logger = LoggerFactory.getLogger(PushNotifier.class);

    public PushNotifier() throws Exception {
        FileInputStream serviceAccount = new FileInputStream("src/main/resources/service_account_key.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
    }

    @Override
    public void send(String message) {
        try {
            Message msg = Message.builder()
                    .setNotification(Notification.builder()
                            .setTitle("Notification Title")
                            .setBody(message)
                            .build())
                    .setTopic("general")
                    .build();

            String response = FirebaseMessaging.getInstance().send(msg);

//            try {
//                PushNotifier pushNotifier = new PushNotifier();
//                pushNotifier.send("This is a test push notification.");
//            } catch (Exception e) {
//                logger.error(e.getMessage());
//            }

            logger.info("Successfully sent message: {}", response);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}