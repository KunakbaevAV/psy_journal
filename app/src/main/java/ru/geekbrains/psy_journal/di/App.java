package ru.geekbrains.psy_journal.di;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import javax.inject.Inject;

import ru.geekbrains.psy_journal.model.database.DataBaseFirstLoader;

import static ru.geekbrains.psy_journal.Constants.FIRST_START;
import static ru.geekbrains.psy_journal.Constants.PREFERENCES;
import static ru.geekbrains.psy_journal.Constants.TAG;

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
        if (checkIsFirstStart()) dataBaseFirstLoader.initDataBase();
    }

    private boolean checkIsFirstStart() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        boolean isFirstStart = sharedPreferences.getBoolean(FIRST_START, true);
        if (isFirstStart) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(FIRST_START, false);
            editor.apply();
            return true;
        } else {
            return false;
        }
    }
}
