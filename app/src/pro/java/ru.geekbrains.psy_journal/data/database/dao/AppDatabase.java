package ru.geekbrains.psy_journal.data.database.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ru.geekbrains.psy_journal.data.repositories.model.Category;
import ru.geekbrains.psy_journal.data.repositories.model.Group;
import ru.geekbrains.psy_journal.data.repositories.model.Journal;
import ru.geekbrains.psy_journal.data.repositories.model.OTF;
import ru.geekbrains.psy_journal.data.repositories.model.TD;
import ru.geekbrains.psy_journal.data.repositories.model.TF;
import ru.geekbrains.psy_journal.data.repositories.model.WorkForm;

@Database(entities = {Category.class, Group.class, Journal.class, OTF.class, TF.class, TD.class, WorkForm.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CategoryDao categoryDao();

    public abstract GroupDao groupDao();

    public abstract JournalDao journalDao();

    public abstract OTFDao otfDao();

    public abstract TFDao tfDao();

    public abstract TDDao tdDao();

    public abstract WorkFormDao workFormDao();

    public abstract ReportDao reportDao();

    public abstract TableCleaningDao tableCleaningDao();

	public abstract AddedCatalogDao addedCatalogDao();
}
