package ru.geekbrains.psy_journal.model.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import ru.geekbrains.psy_journal.model.data.ReportData;

@Dao
public interface ReportDao {

    String queryReportFromTF = "SELECT TF.name AS nameTF, SUM(Journal.quantityPeople) AS quantityPeople, SUM(Journal.workTime) AS workTime FROM Journal LEFT JOIN\n" +
            " \n" +
            "TD ON Journal.codeTD = TD.code\n" +
            " \n LEFT JOIN TF ON TD.idTF = TF.id " +
            "WHERE TF.idOTF = :idOTF AND Journal.date BETWEEN :dateFrom AND :dateTo " +
            "GROUP BY nameTF";

    @Query(queryReportFromTF)
    List<ReportData> getReport(int idOTF, long dateFrom, long dateTo);

}
