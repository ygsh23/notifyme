package com.notifyme.app;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Properties;

import static org.mockito.Mockito.*;

public class EmailNotifierTest {

    @Mock
    private Logger logger;

    @Mock
    private Session session;

    @Mock
    private Transport transport;

    @InjectMocks
    private EmailNotifier emailNotifier;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendEmailSuccess() throws Exception {
        // Prepare test data
        String message = "Test message";
        Properties properties = new Properties();
        Session session = Session.getInstance(properties);

        // Mock MimeMessage
        MimeMessage mimeMessage = mock(MimeMessage.class);
//        whenNew(MimeMessage.class).withAnyArguments().thenReturn(mimeMessage);

        // Call the method to test
        emailNotifier.send(message);

        // Verify that the email was sent
        verify(mimeMessage).setText(message);
        verify(mimeMessage).setSubject(anyString());
        verify(mimeMessage).setFrom(any(InternetAddress.class));
        verify(mimeMessage).addRecipient(any(), any(InternetAddress.class));
        verify(transport).send(mimeMessage);
    }

    @Test
    public void testSendEmailFailure() throws Exception {
        // Mock exceptions
        doThrow(new RuntimeException("Mail Exception")).when(transport).send(any(MimeMessage.class));

        // Call the method to test
        emailNotifier.send("Test message");

        // Verify error logging
        verify(logger).error(anyString());
    }
}

