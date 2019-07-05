package ru.geekbrains.psy_journal.model.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.apache.commons.math3.analysis.function.Sin;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.geekbrains.psy_journal.model.data.WorkForm;

@Dao
public interface WorkFormDao {

    @Query("SELECT * FROM Work_form")
    Single<List<WorkForm>> getAllWorkForms();

    @Query("SELECT * FROM Work_form WHERE id = :id")
    Single<WorkForm> getItemWorkWorm(int id);

    @Insert
    Single<Long> insert(WorkForm workForm);

    @Delete
    Single<Integer> delete(WorkForm workForm);

    @Update
    Single<Integer> update(WorkForm workForm);

    //первичная инициализация
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertList(List<WorkForm> workFormList);

}
