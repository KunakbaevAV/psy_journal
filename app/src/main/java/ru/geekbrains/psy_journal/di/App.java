package ru.geekbrains.psy_journal.di;

import android.app.Application;
import android.content.SharedPreferences;
import org.xmlpull.v1.XmlPullParser;
import javax.inject.Inject;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.data.files.FileXMLLoader;
import ru.geekbrains.psy_journal.data.files.LoadableDataBase;
import static ru.geekbrains.psy_journal.Constants.FIRST_START;
import static ru.geekbrains.psy_journal.Constants.PREFERENCES;

public class App extends Application {

    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Inject LoadableDataBase loadableDataBase;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
	            .fileModule(new FileModule(this))
                .build();
        appComponent.inject(this);
        checkIsFirstStart();
    }

    private void checkIsFirstStart() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        boolean isFirstStart = sharedPreferences.getBoolean(FIRST_START, true);
        if (isFirstStart) {
        	loadDefaultFunctions();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(FIRST_START, false);
            editor.apply();
        }
    }

	private void loadDefaultFunctions(){
		XmlPullParser parser = getApplicationContext().getResources().getXml(R.xml.default_functions);
		new FileXMLLoader(loadableDataBase, parser).toParseFile(null);
	}
}
