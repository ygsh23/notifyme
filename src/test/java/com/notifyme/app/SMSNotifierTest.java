package com.notifyme.app;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

public class SMSNotifierTest {

    @Mock
    private Logger logger;

    @InjectMocks
    private SMSNotifier smsNotifier;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendSMSSuccess() {
        // Mock Twilio Message creation
        Message mockMessage = mock(Message.class);
        when(mockMessage.getSid()).thenReturn("SM123456789");

        // Call the method to test
        smsNotifier.send("Test SMS message");

        // Verify that the SMS was sent
        verify(logger).info("SMS sent successfully: {}", "SM123456789");
    }

    @Test
    public void testSendSMSFailure() {
        // Mock exception
        doThrow(new RuntimeException("Twilio Exception")).when(Message.class);

        // Call the method to test
        smsNotifier.send("Test SMS message");

        // Verify error logging
        verify(logger).error(anyString(), any(Throwable.class));
    }
}

