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
import ru.geekbrains.psy_journal.model.data.TD;

@Dao
public interface TDDao {

    @Query("SELECT * FROM TD")
    Single<List<TD>> getAllTd();

    @Query("SELECT * FROM TD WHERE idTF = :idTF")
    Single<List<TD>> getTdByTf(int idTF);

	//первичная инициализация
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	Completable insert(List<TD> tdList);

    @Insert
    long insert(TD td);

    @Delete
    int delete(TD td);

    @Update
    int update(TD td);

}
