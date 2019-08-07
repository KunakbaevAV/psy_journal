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
import ru.geekbrains.psy_journal.data.repositories.model.TD;

@Dao
public interface TDDao {

    @Query("SELECT * FROM TD")
    Single<List<TD>> getAllTd();

    @Query("SELECT * FROM TD WHERE idTF = :idTF")
    Single<List<TD>> getTdByTf(int idTF);

    @Query("SELECT * FROM TD WHERE id = :id")
    Single<TD> getItemTD(int id);

    @Query("SELECT * FROM TD WHERE code = :code")
    Single<TD> getTdByCode(String code);

    @Query("DELETE FROM TD")
    Completable deleteAllTD();

	//первичная инициализация
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	Completable insert(List<TD> tdList);

    @Insert
    Completable insert(TD td);

    @Delete
    Completable delete(TD td);

    @Update
    Completable update(TD td);

}
