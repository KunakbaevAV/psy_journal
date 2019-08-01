package ru.geekbrains.psy_journal.data.database.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import io.reactivex.Single;
import ru.geekbrains.psy_journal.domain.models.ReportData;

/**
 * Класс формирования запроса SQL для получения отчета о проделанной работе пользователя
 */
@Dao
public interface ReportDao {

    String queryReportFromTF = "SELECT " +
            "TF.code AS codeTF, " +
            "TF.name AS nameTF, " +
            "SUM(Journal.quantityPeople) AS quantityPeople, " +
            "SUM(Journal.workTime) AS workTime " +
            "FROM TF " +
            "LEFT JOIN Journal ON TF.id =(" +
            "SELECT TD.idTF FROM TD WHERE TD.code = Journal.codeTD)" +
            " AND Journal.date BETWEEN :dateFrom AND :dateTo " +
            "WHERE TF.idOTF = :idOTF " +
            "GROUP BY TF.id";

    String queryReportFromTD = "SELECT " +
            "TD.code AS codeTF, " +
            "TD.name AS nameTF, " +
            "SUM(Journal.quantityPeople) AS quantityPeople, " +
            "SUM(Journal.workTime) AS workTime " +
            "FROM TD " +
            "LEFT JOIN Journal ON TD.code = Journal.codeTD " +
            " WHERE TD.idTF = (SELECT TF.id FROM TF WHERE TF.code = :codeTF)" +
            "AND Journal.date BETWEEN :dateFrom AND :dateTo " +
            "GROUP BY TD.id";

    @Transaction
    @Query(queryReportFromTF)
    Single<List<ReportData>> getReport(int idOTF, long dateFrom, long dateTo);

    @Transaction
    @Query(queryReportFromTD)
    Single<List<ReportData>> getReportByTF(String codeTF, long dateFrom, long dateTo);
}
