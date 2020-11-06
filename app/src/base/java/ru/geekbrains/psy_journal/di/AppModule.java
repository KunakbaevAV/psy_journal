package ru.geekbrains.psy_journal.di;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.psy_journal.data.database.DataBaseLoader;
import ru.geekbrains.psy_journal.data.database.dao.AddedCatalogDao;
import ru.geekbrains.psy_journal.data.database.dao.AppDatabase;
import ru.geekbrains.psy_journal.data.database.dao.CategoryDao;
import ru.geekbrains.psy_journal.data.database.dao.GroupDao;
import ru.geekbrains.psy_journal.data.database.dao.JournalDao;
import ru.geekbrains.psy_journal.data.database.dao.OTFDao;
import ru.geekbrains.psy_journal.data.database.dao.ReportDao;
import ru.geekbrains.psy_journal.data.database.dao.TDDao;
import ru.geekbrains.psy_journal.data.database.dao.TFDao;
import ru.geekbrains.psy_journal.data.database.dao.WorkFormDao;
import ru.geekbrains.psy_journal.data.files.LoadableDataBase;
import ru.geekbrains.psy_journal.data.repositories.Loadable;
import ru.geekbrains.psy_journal.data.repositories.Mapping;
import ru.geekbrains.psy_journal.data.repositories.RoomHelper;
import ru.geekbrains.psy_journal.data.repositories.StorableCategory;
import ru.geekbrains.psy_journal.data.repositories.StorableGroup;
import ru.geekbrains.psy_journal.data.repositories.StorableJournal;
import ru.geekbrains.psy_journal.data.repositories.StorableOTF;
import ru.geekbrains.psy_journal.data.repositories.StorableReportingJournals;
import ru.geekbrains.psy_journal.data.repositories.StorableTD;
import ru.geekbrains.psy_journal.data.repositories.StorableTF;
import ru.geekbrains.psy_journal.data.repositories.StorableWorkForm;

import static ru.geekbrains.psy_journal.Constants.DATABASE_NAME;

@Module
public class AppModule {

    private final Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    RoomHelper getRoomHelper() {
        return new RoomHelper();
    }

    @Provides
    JournalDao getJournalDao() {
        return getAppDatabase().journalDao();
    }

    @Provides
    OTFDao getOTFDao() {
        return getAppDatabase().otfDao();
    }

    @Provides
    TFDao getTFDao() {
        return getAppDatabase().tfDao();
    }

    @Provides
    TDDao getTDDao() {
        return getAppDatabase().tdDao();
    }

    @Provides
    CategoryDao getCategoryDao() {
        return getAppDatabase().categoryDao();
    }

    @Provides
    GroupDao getGroupDao() {
        return getAppDatabase().groupDao();
    }

    @Provides
    WorkFormDao getWorkFormDao() {
        return getAppDatabase().workFormDao();
    }

	@Provides
	AddedCatalogDao getAddedCatalogDao() {
		return getAppDatabase().addedCatalogDao();
	}

    @Provides
    AppDatabase getAppDatabase() {
        return Room.databaseBuilder(context,
                AppDatabase.class, DATABASE_NAME).build();
    }

    @Provides
    @Singleton
    LoadableDataBase provideLoadableDataBase(Loadable loadable) {
        return new DataBaseLoader(context, loadable);
    }

    @Provides
    ReportDao getReportDao() {
        return getAppDatabase().reportDao();
    }

    @Provides
    @Singleton
    StorableReportingJournals provideMapping() {
        return new Mapping();
    }

	@Provides
	@Singleton
	StorableCategory provideStorableCategory(RoomHelper roomHelper){
    	return roomHelper;
	}

	@Provides
	@Singleton
	Loadable provideLoadable(RoomHelper roomHelper){
		return roomHelper;
	}

	@Provides
	@Singleton
	StorableGroup provideStorableGroup(RoomHelper roomHelper){
		return roomHelper;
	}

	@Provides
	@Singleton
	StorableWorkForm provideStorableWorkForm(RoomHelper roomHelper){
		return roomHelper;
	}

	@Provides
	@Singleton
	StorableJournal provideStorableJournal(RoomHelper roomHelper){
		return roomHelper;
	}

	@Provides
	@Singleton
	StorableOTF provideStorableOTF(RoomHelper roomHelper){
		return roomHelper;
	}

	@Provides
	@Singleton
	StorableTF provideStorableTF(RoomHelper roomHelper){
		return roomHelper;
	}

	@Provides
	@Singleton
	StorableTD provideStorableTD(RoomHelper roomHelper){
		return roomHelper;
	}

	@Provides
	@Singleton
	InputMethodManager provideInputMethodManager(){
        return (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
	}
}
