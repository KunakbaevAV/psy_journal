package ru.geekbrains.psy_journal.model.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;
import ru.geekbrains.psy_journal.model.data.Journal;

@Dao
public interface JournalDao {

    @Query("SELECT * FROM Journal")
    List<Journal> getAll();

    @Insert
    long insert(Journal journal);

    @Delete
    int delete(Journal journal);

    @Update
    int update(Journal journal);

}
