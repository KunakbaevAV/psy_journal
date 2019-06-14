package ru.geekbrains.psy_journal.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

import ru.geekbrains.psy_journal.model.database.DataBaseFirstLoader;

public class App extends Application {

    private final String FIRST_START = "first_start";
    private boolean isFirstStart;
    private SharedPreferences mSetting;

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

//        checkFirstStart();
//        initDB();
    }

    private void initDB() {
        if (isFirstStart) {
            dataBaseFirstLoader.initDataBase();
            SharedPreferences.Editor editor = mSetting.edit();
            editor.putBoolean(FIRST_START, false);
            editor.apply();
        }
    }

    private void checkFirstStart() {
        mSetting = getSharedPreferences(FIRST_START, Context.MODE_PRIVATE);
        isFirstStart = mSetting.getBoolean(FIRST_START, true);
    }
}
