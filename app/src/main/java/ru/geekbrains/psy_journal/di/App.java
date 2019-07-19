package ru.geekbrains.psy_journal.di;

import android.app.Application;
import android.content.SharedPreferences;

import javax.inject.Inject;

import ru.geekbrains.psy_journal.data.database.DataBaseLoader;

import static ru.geekbrains.psy_journal.Constants.FIRST_START;
import static ru.geekbrains.psy_journal.Constants.PREFERENCES;

public class App extends Application {

    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

//    @Inject
//    DataBaseLoader dataBaseLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
	            .fileModule(new FileModule(this))
                .build();
        appComponent.inject(this);
//        if (checkIsFirstStart()) dataBaseLoader.initDataBase();
    }

//    private boolean checkIsFirstStart() {
//        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
//        boolean isFirstStart = sharedPreferences.getBoolean(FIRST_START, true);
//        if (isFirstStart) {
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putBoolean(FIRST_START, false);
//            editor.apply();
//            return true;
//        } else {
//            return false;
//        }
//    }
}
