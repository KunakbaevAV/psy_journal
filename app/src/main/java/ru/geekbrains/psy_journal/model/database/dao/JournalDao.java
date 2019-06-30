package ru.geekbrains.psy_journal.model.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;
import ru.geekbrains.psy_journal.model.data.Journal;

@Dao
public interface JournalDao {

    String queryReportFromTF = "SELECT Journal.id, Journal.date, Journal.codeTd,\n" +
            "                   Journal.idCategory, Journal.idGroup, Journal.name,\n" +
            "                   Journal.quantityPeople, Journal.declaredRequest,\n" +
            "                   Journal.realRequest, Journal.idWorkForm,\n" +
            "                   Journal.workTime, Journal.comment FROM Journal LEFT JOIN\n" +
            " \n" +
            "TD ON Journal.codeTD = TD.code\n" +
            " \n LEFT JOIN TF ON TD.idTF = TF.id " +
            "WHERE TF.idOTF = :idOTF AND Journal.date BETWEEN :dateFrom AND :dateTo " +
            "ORDER BY date";

    @Query("SELECT * FROM Journal")
    List<Journal> getAll();

    @Query("SELECT * FROM Journal WHERE id = :id")
    Journal getItemJournal(int id);

    @Insert
    long insert(Journal journal);

    @Delete
    int delete(Journal journal);

    @Update
    int update(Journal journal);

    @Query(queryReportFromTF)
    Single<List<Journal>> getLaborFunctionReport(int idOTF, long dateFrom, long dateTo);

    @Query("SELECT Journal.name FROM Journal WHERE Journal.name IS NOT NULL")
    Single<List<String>> getListFullNames();
}
