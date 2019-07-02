package ru.geekbrains.psy_journal.model.database;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.model.data.ReportData;
import ru.geekbrains.psy_journal.model.data.TF;
import ru.geekbrains.psy_journal.model.database.dao.ReportDao;
import ru.geekbrains.psy_journal.model.database.dao.TFDao;

import static ru.geekbrains.psy_journal.Constants.TAG;

/**
 * Класс для получения отчета из базы данных
 */
public class ReportHelper {

    private TFDao tfDao;
    private ReportDao reportDao;
    private List<TF> tfList = new ArrayList<>();
    private List<ReportData> reportFact = new ArrayList<>();
    private List<ReportData> reportData = new ArrayList<>();
    private int idOTF;
    private long from;
    private long unto;

    ReportHelper(TFDao tfDao, ReportDao reportDao) {
        this.tfDao = tfDao;
        this.reportDao = reportDao;
    }

    /**
     * Метод сохраняет в переменную reportData список объектов {@link ReportData} в следующем формате:
     * если по указанным Трудовым функциям(ТФ) данные были заполнены, подсчитывается сумма часов и человек,
     * если данных по указанным Трудовым функциям нет, названия ТФ есть, сумма часов и человек = 0.
     * Список заполнен по порядку возрастания кода ТФ.
     * Данные заполняются в асинхронных потоках, поэтому список в reportData заполняется не сразу.
     *
     * @param idOTF код Обобщенной ТФ, к которой относятся запрашиваемые ТФ
     * @param from  дата, с которой считается отчет
     * @param unto  дата, по которую считается отчет
     */
    List<ReportData> getReportData(int idOTF, long from, long unto) {
        this.idOTF = idOTF;
        this.from = from;
        this.unto = unto;

        tfList.addAll(tfDao.getTfByOtf(idOTF));
        reportFact.addAll(reportDao.getReport(idOTF, from, unto));

        for (TF tf : tfList) {
            reportData.add(dataFromFactReport(tf));
        }
        return reportData;
    }

    private ReportData dataFromFactReport(TF tf) {
        for (ReportData data : reportFact) {
            if (data.getCodeTF().equals(tf.getCode())) {
                reportFact.remove(data);
                return data;
            }
        }
        return new ReportData(tf.getCode(), tf.getName(), 0, 0);
    }


}
