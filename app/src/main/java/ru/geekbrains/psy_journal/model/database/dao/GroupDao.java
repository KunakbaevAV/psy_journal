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
import ru.geekbrains.psy_journal.model.data.Group;

@Dao
public interface GroupDao {

    @Query("SELECT * FROM `Group`")
    Single<List<Group>> getAllGroups();

    @Query("SELECT * FROM `Group` WHERE id = :id")
    Single<Group> getItemGroup(int id);

    @Insert
    Single<Long> insert(Group group);

    @Delete
    Single<Integer> delete(Group group);

    @Update
    Single<Integer> update(Group group);

    //первичная инициализация
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertList(List<Group> groupList);
}
