package com.notifyme.app;

import com.google.inject.Inject;

import java.util.Set;

public class NotificationService {

    private final Set<Notifier> notifiers;
    private final LoggerService loggerService;

    @Inject
    public NotificationService(Set<Notifier> notifiers, LoggerService loggerService) {
        this.notifiers = notifiers;
        this.loggerService = loggerService;
    }

    public void notifyUser(String message) {
        for (Notifier notifier : notifiers) {
            notifier.send(message);
            loggerService.log("Sent notification: " + message);
        }
    }
}