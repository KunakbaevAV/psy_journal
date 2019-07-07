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
import ru.geekbrains.psy_journal.data.repositories.model.Group;

@Dao
public interface GroupDao {

    @Query("SELECT * FROM `Group`")
    Single<List<Group>> getAllGroups();

    @Query("SELECT * FROM `Group` WHERE id = :id")
    Single<Group> getItemGroup(int id);

    @Insert
    Completable insert(Group group);

    @Delete
    Completable delete(Group group);

    @Update
    Completable update(Group group);

    //первичная инициализация
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertList(List<Group> groupList);
}
