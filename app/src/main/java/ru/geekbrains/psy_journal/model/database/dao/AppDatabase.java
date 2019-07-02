package ru.geekbrains.psy_journal.model.database.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ru.geekbrains.psy_journal.model.data.Category;
import ru.geekbrains.psy_journal.model.data.Group;
import ru.geekbrains.psy_journal.model.data.Journal;
import ru.geekbrains.psy_journal.model.data.OTF;
import ru.geekbrains.psy_journal.model.data.TD;
import ru.geekbrains.psy_journal.model.data.TF;
import ru.geekbrains.psy_journal.model.data.WorkForm;

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
}
