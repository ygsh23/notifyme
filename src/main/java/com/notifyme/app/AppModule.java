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

        bind(LoggerService.class).asEagerSingleton();
    }

//    dataSource.setUrl("jdbc:mariadb://localhost:3306/notify_me");
//    dataSource.setUser("root");
//    dataSource.setPassword("11223344");

//    may be user admin here not root
    @Provides
    public DataSource provideDataSource() {
        try {
            MariaDbDataSource dataSource = new MariaDbDataSource();
            dataSource.setUrl("jdbc:mariadb://notify-me.c9ac6eemk79i.us-east-1.rds.amazonaws.com:3306/notify_me");
            dataSource.setUser("admin");
            dataSource.setPassword("11223344");
            return dataSource;
        } catch (Exception e) {
            throw new RuntimeException("Error setting up the data source", e);
        }
    }
}

