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

    @Mock
    private PushNotifier pushNotifier;

    @InjectMocks
    private NotificationService notificationService;

    private AutoCloseable mocks;

    @BeforeMethod
    public void setUp() {
        // Initialize mocks and store the AutoCloseable reference
        mocks = MockitoAnnotations.openMocks(this);

        // Manually inject the set of notifiers
        Set<Notifier> notifiers = new HashSet<>();
        notifiers.add(emailNotifier);
        notifiers.add(smsNotifier);
        notifiers.add(pushNotifier);

        notificationService = new NotificationService(notifiers, loggerService);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        // Ensure mocks are closed after each test
        if (mocks != null) {
            mocks.close();
        }
    }

    @Test
    public void testNotifyUser() {
        String message = "Test Notification";

        // Call the method under test
        notificationService.notifyUser(message);

        // Verify that each notifier was called once
        Mockito.verify(emailNotifier).send(message);
        Mockito.verify(smsNotifier).send(message);
        Mockito.verify(pushNotifier).send(message);

        // Verify that logging happened for each notification
        Mockito.verify(loggerService, Mockito.times(3)).log(Mockito.anyString());
    }
}
