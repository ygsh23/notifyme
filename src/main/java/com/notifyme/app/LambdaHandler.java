package com.notifyme.app;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.Map;

public class LambdaHandler implements RequestHandler<Map<String, Object>, String> {

    @Override
    public String handleRequest(Map<String, Object> event, Context context) {
        Injector injector = Guice.createInjector(new AppModule());
        NotificationService notificationService = injector.getInstance(NotificationService.class);

        // Example of sending a notification
        notificationService.notifyUser((String) event.get("message"));  // Use the input message for notification
        return (String) event.get("message");
    }
}
