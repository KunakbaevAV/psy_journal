package ru.geekbrains.psy_journal.data.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.geekbrains.psy_journal.data.repositories.model.Journal;

@Dao
public interface JournalDao {

    @Query("SELECT * FROM Journal")
    Single<List<Journal>> getAll();

    @Query("SELECT * FROM Journal")
    List<Journal> getAllSimple();

    @Query("SELECT * FROM Journal WHERE id = :id")
    Single<Journal> getItemJournal(int id);

    @Insert
    Completable insert(Journal journal);

    @Delete
    Completable delete(Journal journal);

    @Update
    Completable update(Journal journal);

    @Query("SELECT Journal.name FROM Journal WHERE Journal.name IS NOT NULL")
    Single<List<String>> getListFullNames();

    @Query("DELETE FROM Journal")
    Completable deleteAllJournal();
}
