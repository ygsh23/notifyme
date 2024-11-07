package com.notifyme.app;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import com.twilio.rest.api.v2010.account.MessageCreator;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class SMSNotifierTest {

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendSMSSuccess() throws Exception {
        Message mockMessage = mock(Message.class);
        when(mockMessage.getSid()).thenReturn("SM123456789");

        MessageCreator mockMessageCreator = mock(MessageCreator.class);
        when(mockMessageCreator.create()).thenReturn(mockMessage);

        try (var messageMock = mockStatic(Message.class)) {
            messageMock.when(() -> Message.creator(any(PhoneNumber.class), any(PhoneNumber.class), anyString()))
                    .thenReturn(mockMessageCreator);

            DataSource dataSource = mock(DataSource.class);
            Connection mockConnection = mock(Connection.class);
            PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
            SMSNotifier smsNotifier = new SMSNotifier(dataSource);

            when(dataSource.getConnection()).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

            smsNotifier.send("Test SMS message");

            verify(mockPreparedStatement).setString(1, "+917973467958");
            verify(mockPreparedStatement).setString(2, "Test SMS message");
            verify(mockPreparedStatement).setBoolean(3, true);
            verify(mockPreparedStatement).executeUpdate();
        }
    }

    @Test
    public void testSendSMSFailure() throws Exception {
        MessageCreator mockMessageCreator = mock(MessageCreator.class);

        try (var messageMock = mockStatic(Message.class)) {
            messageMock.when(() -> Message.creator(any(PhoneNumber.class), any(PhoneNumber.class), anyString()))
                    .thenReturn(mockMessageCreator);

            DataSource dataSource = mock(DataSource.class);
            Connection mockConnection = mock(Connection.class);
            PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
            SMSNotifier smsNotifier = new SMSNotifier(dataSource);

            when(dataSource.getConnection()).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

            smsNotifier.send("Test SMS message");

            verify(mockPreparedStatement).setString(1, "+917973467958");
            verify(mockPreparedStatement).setString(2, "Test SMS message");
            verify(mockPreparedStatement).setBoolean(3, false);
            verify(mockPreparedStatement).executeUpdate();
        }
    }
}

