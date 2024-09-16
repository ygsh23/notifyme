package com.notifyme.app;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainApp {
    private static final Logger logger = LoggerFactory.getLogger(MainApp.class);

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AppModule());
        NotificationService notificationService = injector.getInstance(NotificationService.class);
        notificationService.notifyUser("Your order has been shipped!");
    }
}
