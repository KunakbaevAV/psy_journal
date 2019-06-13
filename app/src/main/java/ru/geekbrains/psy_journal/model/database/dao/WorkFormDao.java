package ru.geekbrains.psy_journal.model.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;
import ru.geekbrains.psy_journal.model.data.WorkForm;

@Dao
public interface WorkFormDao {

    @Query("SELECT * FROM Work_form")
    Single<List<WorkForm>> getAllWorkForms();

    @Insert
    long insert(WorkForm workForm);

    @Delete
    int delete(WorkForm workForm);

    @Update
    int update(WorkForm workForm);

}
