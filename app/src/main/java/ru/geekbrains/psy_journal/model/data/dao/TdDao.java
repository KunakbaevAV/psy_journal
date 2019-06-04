package ru.geekbrains.psy_journal.model.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;
import ru.geekbrains.psy_journal.model.data.Td;

@Dao
public interface TdDao {

    @Query("SELECT * FROM Td")
    Single<List<Td>> getAllTd();

    @Insert
    long insert(Td td);

    @Delete
    int delete(Td td);

    @Update
    int update(Td td);

}
