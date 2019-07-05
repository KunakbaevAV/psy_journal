package ru.geekbrains.psy_journal.model.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.geekbrains.psy_journal.model.data.OTF;

@Dao
public interface OTFDao {

    @Query("SELECT * FROM OTF")
    Single<List<OTF>> getAllOtf();

    @Query("SELECT * FROM OTF WHERE id = :id")
    Single<OTF> getItemOtf(int id);

    //первичная инициализация
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	Completable insert(List<OTF> otfList);

	@Insert
    Completable insert(OTF otf);

    @Delete
    Completable delete(OTF otf);

    @Update
    Completable update(OTF otf);

}
