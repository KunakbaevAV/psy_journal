package ru.geekbrains.psy_journal.model.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;
import ru.geekbrains.psy_journal.model.data.Otf;

@Dao
public interface OtfDao {

    @Query("SELECT * FROM Otf")
    Single<List<Otf>> getAllOtf();

    @Insert
    long insert(Otf otf);

    @Delete
    int delete(Otf otf);

    @Update
    int update(Otf otf);

}
