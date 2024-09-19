package com.notifyme.app;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.multibindings.Multibinder;

import javax.sql.DataSource;
import org.mariadb.jdbc.MariaDbDataSource;

public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<Notifier> notifierBinder = Multibinder.newSetBinder(binder(), Notifier.class);
        notifierBinder.addBinding().to(EmailNotifier.class);
        notifierBinder.addBinding().to(SMSNotifier.class);
        notifierBinder.addBinding().to(PushNotifier.class);

        bind(LoggerService.class).asEagerSingleton();
    }

    @Provides
    public DataSource provideDataSource() {
        try {
            MariaDbDataSource dataSource = new MariaDbDataSource();
            dataSource.setUrl("jdbc:mariadb://localhost:3306/notify_me");
            dataSource.setUser("root");
            dataSource.setPassword("11223344");
            return dataSource;
        } catch (Exception e) {
            throw new RuntimeException("Error setting up the data source", e);
        }
    }
}

