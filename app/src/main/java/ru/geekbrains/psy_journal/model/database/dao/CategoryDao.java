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
import ru.geekbrains.psy_journal.model.data.Category;

@Dao
public interface CategoryDao {

    @Query("SELECT * FROM Category")
    Single<List<Category>> getAllCategories();

    @Query("SELECT * FROM Category WHERE id = :id")
    Single<Category> getItemCategory(int id);

    @Insert
    Completable insert(Category category);

    @Delete
    Completable delete(Category category);

    @Update
    Completable update(Category category);

    //первичная инициализация
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertList(List<Category> categoryList);

}
