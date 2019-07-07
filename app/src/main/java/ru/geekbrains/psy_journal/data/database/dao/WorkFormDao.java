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
import ru.geekbrains.psy_journal.data.repositories.model.WorkForm;

@Dao
public interface WorkFormDao {

    @Query("SELECT * FROM Work_form")
    Single<List<WorkForm>> getAllWorkForms();

    @Query("SELECT * FROM Work_form WHERE id = :id")
    Single<WorkForm> getItemWorkWorm(int id);

    @Insert
    Completable insert(WorkForm workForm);

    @Delete
    Completable delete(WorkForm workForm);

    @Update
    Completable update(WorkForm workForm);

    //первичная инициализация
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertList(List<WorkForm> workFormList);

}
