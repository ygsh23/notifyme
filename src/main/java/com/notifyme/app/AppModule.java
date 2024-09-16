package com.notifyme.app;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<Notifier> notifierBinder = Multibinder.newSetBinder(binder(), Notifier.class);
        notifierBinder.addBinding().to(EmailNotifier.class);
        notifierBinder.addBinding().to(SMSNotifier.class);
        notifierBinder.addBinding().to(PushNotifier.class);

        bind(LoggerService.class).asEagerSingleton();
    }
}

