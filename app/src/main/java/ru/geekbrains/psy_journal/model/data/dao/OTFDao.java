package ru.geekbrains.psy_journal.model.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;
import ru.geekbrains.psy_journal.model.data.OTF;

@Dao
public interface OTFDao {

    @Query("SELECT * FROM OTF")
    Single<List<OTF>> getAllOtf();

    @Insert
    long insert(OTF otf);

    @Delete
    int delete(OTF otf);

    @Update
    int update(OTF otf);

}
