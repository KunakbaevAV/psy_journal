package ru.geekbrains.psy_journal.model.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;
import ru.geekbrains.psy_journal.model.data.TD;

@Dao
public interface TDDao {

    @Query("SELECT * FROM TD")
    Single<List<TD>> getAllTd();

    @Insert
    long insert(TD td);

    @Delete
    int delete(TD td);

    @Update
    int update(TD td);

}
