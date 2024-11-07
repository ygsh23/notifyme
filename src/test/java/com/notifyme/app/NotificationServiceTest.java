package com.notifyme.app;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

public class NotificationServiceTest {

    @Mock
    private LoggerService loggerService;

    @Mock
    private EmailNotifier emailNotifier;

    @Mock
    private SMSNotifier smsNotifier;

    @InjectMocks
    private NotificationService notificationService;

    private AutoCloseable mocks;

    @BeforeMethod
    public void setUp() {
        mocks = MockitoAnnotations.openMocks(this);

        Set<Notifier> notifiers = new HashSet<>();
        notifiers.add(emailNotifier);
        notifiers.add(smsNotifier);

        notificationService = new NotificationService(notifiers, loggerService);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        if (mocks != null) {
            mocks.close();
        }
    }

    @Test
    public void testNotifyUser() {
        String message = "Test Notification";

        notificationService.notifyUser(message);

        Mockito.verify(emailNotifier).send(message);
        Mockito.verify(smsNotifier).send(message);

        Mockito.verify(loggerService, Mockito.times(3)).log(Mockito.anyString());
    }
}
