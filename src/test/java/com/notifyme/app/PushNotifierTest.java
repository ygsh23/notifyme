package com.notifyme.app;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

public class PushNotifierTest {

    @Mock
    private FirebaseMessaging firebaseMessaging;

    @Mock
    private Logger logger;

    @InjectMocks
    private PushNotifier pushNotifier;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendPushNotificationSuccess() throws Exception {
        // Mock Firebase Message creation
        Message mockMessage = mock(Message.class);

        // Mock Firebase response
        when(firebaseMessaging.send(any(Message.class))).thenReturn("message_id");

        // Call the method to test
        pushNotifier.send("Test push message");

        // Verify that the push notification was sent
        verify(logger).info("Successfully sent message: message_id");
    }

    @Test
    public void testSendPushNotificationFailure() throws Exception {
        // Mock exception
        doThrow(new RuntimeException("Firebase Exception")).when(firebaseMessaging).send(any(Message.class));

        // Call the method to test
        pushNotifier.send("Test push message");

        // Verify error logging
        verify(logger).error(anyString(), any(Throwable.class));
    }
}

