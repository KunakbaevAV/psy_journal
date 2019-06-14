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
import ru.geekbrains.psy_journal.model.data.TD;
import ru.geekbrains.psy_journal.model.data.TF;

@Dao
public interface TFDao {

    @Query("SELECT * FROM TF")
    Single<List<TF>> getAllTf();

    @Query("SELECT * FROM TF WHERE idOTF = :idOTF")
    Single<List<TF>> getTfByOtf(int idOTF);

	//первичная инициализация
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	Completable insert(List<TF> tfList);

    @Insert
    long insert(TF tf);

    @Delete
    int delete(TF tf);

    @Update
    int update(TF tf);

}
