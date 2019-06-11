package ru.geekbrains.psy_journal.model.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;
import ru.geekbrains.psy_journal.model.data.Category;

@Dao
public interface CategoryDao {

    @Query("SELECT * FROM Category")
    Single<List<Category>> getAllCategories();

    @Insert
    long insert(Category category);

    @Delete
    int delete(Category category);

    @Update
    int update(Category category);

}