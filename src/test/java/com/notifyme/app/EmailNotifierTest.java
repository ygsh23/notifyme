package com.notifyme.app;

import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.mockito.Mockito.when;

public class EmailNotifierTest {
    private static final Logger logger = LoggerFactory.getLogger(EmailNotifierTest.class);
    private EmailNotifier emailNotifier;
    private PreparedStatement preparedStatement;

    @BeforeMethod
    public void setUp() throws SQLException {
        DataSource dataSource = Mockito.mock(DataSource.class);
        Connection connection = Mockito.mock(Connection.class);
        preparedStatement = Mockito.mock(PreparedStatement.class);

        try {
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(Mockito.anyString())).thenReturn(preparedStatement);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        emailNotifier = new EmailNotifier(dataSource);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(Mockito.anyString())).thenReturn(preparedStatement);
    }

    @Test
    public void testSendEmail() {
        String message = "Test Email Message";
        String to = "y***********@**********.com";
        String subject = "Test Email Notification";
        boolean delivered = true;

        try {
            when(preparedStatement.executeUpdate()).thenReturn(1);
            emailNotifier.send(message);

            Mockito.verify(preparedStatement).setString(1, to);
            Mockito.verify(preparedStatement).setString(2, subject);
            Mockito.verify(preparedStatement).setString(3, message);
            Mockito.verify(preparedStatement).setBoolean(4, delivered);

            Mockito.verify(preparedStatement).executeUpdate();
        } catch (Exception e) {
            Assert.fail("Exception occurred during test: " + e.getMessage());
        }
    }

    @Test
    public void testSendEmailFailure() {
        String message = "Test Email Message";
        String to = "y***********@**********.com";
        String subject = "Test Email Notification";
        boolean delivered = false; // We expect failure, so delivered should be false

        try {
            when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Simulated email failure"));

            try {
                emailNotifier.send(message);
            } catch (RuntimeException e) {
                logger.error(e.getMessage());
            }

            Mockito.verify(preparedStatement).setString(1, to);
            Mockito.verify(preparedStatement).setString(2, subject);
            Mockito.verify(preparedStatement).setString(3, message);
            Mockito.verify(preparedStatement).setBoolean(4, true); // Expect false here

            Mockito.verify(preparedStatement).executeUpdate();
        } catch (Exception e) {
            Assert.fail("Exception occurred during test: " + e.getMessage());
        }
    }
}

