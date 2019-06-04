package ru.geekbrains.psy_journal.model.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;
import ru.geekbrains.psy_journal.model.data.Tf;

@Dao
public interface TfDao {

    @Query("SELECT * FROM Tf")
    Single<List<Tf>> getAllTf();

    @Insert
    long insert(Tf tf);

    @Delete
    int delete(Tf tf);

    @Update
    int update(Tf tf);

}
