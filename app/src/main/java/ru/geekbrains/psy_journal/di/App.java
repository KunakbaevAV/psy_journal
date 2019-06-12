package ru.geekbrains.psy_journal.di;

import android.app.Application;

import ru.geekbrains.psy_journal.model.database.DataBaseFirstLoader;

public class App extends Application {

    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        firstInit();
    }

    private void firstInit() {
        DataBaseFirstLoader dataBaseFirstLoader = new DataBaseFirstLoader();
        dataBaseFirstLoader.initDataBase();
    }
}
