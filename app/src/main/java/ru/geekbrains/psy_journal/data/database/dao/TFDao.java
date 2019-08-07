package ru.geekbrains.psy_journal.data.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.geekbrains.psy_journal.data.repositories.model.TF;

@Dao
public interface TFDao {

    @Query("SELECT * FROM TF")
    Single<List<TF>> getAllTf();

    @Query("SELECT * FROM TF WHERE idOTF = :idOTF")
    Single<List<TF>> getTfByOtf(int idOTF);

    @Query("SELECT * FROM TF WHERE id = :id")
    Single<TF> getItemTF(int id);

    @Query("DELETE FROM TF")
    Completable deleteAllTF();

	//первичная инициализация
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	Completable insert(List<TF> tfList);

    @Insert
    Completable insert(TF tf);

    @Delete
    Completable delete(TF tf);

    @Update
    Completable update(TF tf);

}
