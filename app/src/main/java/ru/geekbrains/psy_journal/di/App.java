package ru.geekbrains.psy_journal.di;

import android.app.Application;

import javax.inject.Inject;

import ru.geekbrains.psy_journal.model.database.DataBaseFirstLoader;

public class App extends Application {

    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Inject
    DataBaseFirstLoader dataBaseFirstLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        appComponent.inject(this);
        dataBaseFirstLoader.initDataBase();
    }
}
