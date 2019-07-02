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

import static ru.geekbrains.psy_journal.Constants.TAG;

/**
 * Класс для получения отчета из базы данных
 */
public class ReportHelper {

    @Inject
    RoomHelper roomHelper;
    private List<TF> tfList = new ArrayList<>();
    private List<ReportData> reportFact = new ArrayList<>();
    private List<ReportData> reportData = new ArrayList<>();
    private int idOTF;
    private long from;
    private long unto;

    public ReportHelper() {
        App.getAppComponent().inject(this);
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
    public void getReportData(int idOTF, long from, long unto) {
        this.idOTF = idOTF;
        this.from = from;
        this.unto = unto;

        initListTF(idOTF);
    }

    private void initListTF(int idOTF) {

        Disposable disposable = roomHelper.getTFList(idOTF)
                .observeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe(
                        list -> {
                            tfList.addAll(list);
                            loadReportFact();
                        },
                        throwable -> Log.e(TAG, "initListTF: " + throwable.getMessage())
                );
    }

    private void loadReportFact() {
        roomHelper.getReport(idOTF, from, unto).observeOn(AndroidSchedulers.mainThread())
                .subscribe(reportList -> {
                    for (ReportData reportData : reportList) {
                        reportFact.add(new ReportData(
                                reportData.getCodeTF(),
                                reportData.getNameTF(),
                                reportData.getQuantityPeople(),
                                reportData.getWorkTime()
                        ));
                    }
                    initReportData();
                }, throwable -> Log.e(TAG, "loadReportFact: " + throwable.getMessage())).isDisposed();
    }

    private void initReportData() {

        for (TF tf : tfList) {
            reportData.add(dataFromFactReport(tf));
        }
        //TODO Передать список reportData для вывода на экран
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
