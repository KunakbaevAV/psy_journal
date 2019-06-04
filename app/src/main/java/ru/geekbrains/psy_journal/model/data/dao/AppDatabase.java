package ru.geekbrains.psy_journal.model.data.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ru.geekbrains.psy_journal.model.data.Category;
import ru.geekbrains.psy_journal.model.data.Group;
import ru.geekbrains.psy_journal.model.data.Journal;
import ru.geekbrains.psy_journal.model.data.Otf;
import ru.geekbrains.psy_journal.model.data.Td;
import ru.geekbrains.psy_journal.model.data.Tf;
import ru.geekbrains.psy_journal.model.data.WorkForm;

@Database(entities = {Category.class, Group.class, Journal.class, Otf.class, Tf.class, Td.class, WorkForm.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CategoryDao categoryDao();

    public abstract GroupDao groupDao();

    public abstract JournalDao journalDao();

    public abstract OtfDao otfDao();

    public abstract TfDao tfDao();

    public abstract TdDao tdDao();

    public abstract WorkFormDao workFormDao();
}