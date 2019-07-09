package ru.geekbrains.psy_journal.data.repositories;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.data.repositories.model.Category;
import ru.geekbrains.psy_journal.data.repositories.model.Group;
import ru.geekbrains.psy_journal.data.repositories.model.Journal;
import ru.geekbrains.psy_journal.data.repositories.model.OTF;
import ru.geekbrains.psy_journal.domain.models.ReportingJournal;
import ru.geekbrains.psy_journal.domain.models.ReportData;
import ru.geekbrains.psy_journal.data.repositories.model.TD;
import ru.geekbrains.psy_journal.data.repositories.model.TF;
import ru.geekbrains.psy_journal.data.repositories.model.WorkForm;
import ru.geekbrains.psy_journal.data.database.dao.CategoryDao;
import ru.geekbrains.psy_journal.data.database.dao.GroupDao;
import ru.geekbrains.psy_journal.data.database.dao.JournalDao;
import ru.geekbrains.psy_journal.data.database.dao.OTFDao;
import ru.geekbrains.psy_journal.data.database.dao.ReportDao;
import ru.geekbrains.psy_journal.data.database.dao.TDDao;
import ru.geekbrains.psy_journal.data.database.dao.TFDao;
import ru.geekbrains.psy_journal.data.database.dao.WorkFormDao;

import static ru.geekbrains.psy_journal.Constants.DB_ADD_ERROR;
import static ru.geekbrains.psy_journal.Constants.DB_ADD_GOOD;
import static ru.geekbrains.psy_journal.Constants.DB_LOGS;
import static ru.geekbrains.psy_journal.Constants.MAPPING_JOURNAL_ERROR;
import static ru.geekbrains.psy_journal.Constants.TAG;

/**
 * Организация работы с базой данный в дополнительном потоке
 */
public class RoomHelper {

    @Inject
    JournalDao journalDao;

    @Inject
    OTFDao otfDao;

    @Inject
    TFDao tfDao;

    @Inject
    TDDao tdDao;

    @Inject
    CategoryDao categoryDao;

    @Inject
    GroupDao groupDao;

    @Inject
    WorkFormDao workFormDao;

    @Inject
    ReportDao reportDao;

    public RoomHelper() {
        App.getAppComponent().inject(this);
    }

    /**
     * Первичная загрузка в базу данных сущностей {@link OTF}
     *
     * @param list загружаемый список сущностей
     */
    public void initializeOTF(List<OTF> list) {
        otfDao.insert(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> Log.i(DB_LOGS, "initializeOTF: " + DB_ADD_GOOD),
                        er -> Log.e(DB_LOGS, "initializeOTF: " + DB_ADD_ERROR, er)
                ).isDisposed();
    }

    /**
     * Первичная загрузка в базу данных сущностей {@link TF}
     *
     * @param list загружаемый список сущностей
     */
    public void initializeTF(List<TF> list) {
        tfDao.insert(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> Log.i(DB_LOGS, "initializeTF: " + DB_ADD_GOOD),
                        er -> Log.e(DB_LOGS, "initializeTF: " + DB_ADD_ERROR, er)
                ).isDisposed();
    }

    /**
     * Первичная загрузка в базу данных сущностей {@link TD}
     *
     * @param list загружаемый список сущностей
     */
    public void initializeTD(List<TD> list) {
        tdDao.insert(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> Log.i(DB_LOGS, "initializeTD: " + DB_ADD_GOOD),
                        er -> Log.e(DB_LOGS, "initializeTD: " + DB_ADD_ERROR, er)
                ).isDisposed();
    }

    /**
     * Первичная загрузка в базу данных сущностей {@link Category}
     *
     * @param list загружаемый список сущностей
     */
    public void initializeCategory(List<Category> list) {
        categoryDao.insertList(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> Log.i(DB_LOGS, "initializeCategory: " + DB_ADD_GOOD),
                        er -> Log.e(DB_LOGS, "initializeCategory: " + DB_ADD_ERROR, er)
                ).isDisposed();
    }

    /**
     * Первичная загрузка в базу данных сущностей {@link Group}
     *
     * @param list загружаемый список сущностей
     */
    public void initializeGroup(List<Group> list) {
        groupDao.insertList(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> Log.i(DB_LOGS, "initializeGroup: " + DB_ADD_GOOD),
                        er -> Log.e(DB_LOGS, "initializeGroup: " + DB_ADD_ERROR, er)
                ).isDisposed();
    }

    /**
     * Первичная загрузка в базу данных сущностей {@link WorkForm}
     *
     * @param list загружаемый список сущностей
     */
    public void initializeWorkForms(List<WorkForm> list) {
        workFormDao.insertList(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> Log.i(DB_LOGS, "initializeWorkForms: " + DB_ADD_GOOD),
                        er -> Log.e(DB_LOGS, "initializeWorkForms: " + DB_ADD_ERROR, er)
                ).isDisposed();
    }

    /**
     * Получение из базы данных списка сущностей {@link Journal}
     *
     * @return список всех сущностей {@link Journal} в базе данных
     */
    public Single<List<Journal>> getJournalList() {
        return journalDao.getAll().subscribeOn(Schedulers.io());
    }

    /**
     * Получение из базы данных списка сущностей {@link OTF}
     *
     * @return список всех сущностей {@link OTF} в базе данных
     */
    public Single<List<OTF>> getOTFList() {
        return otfDao.getAllOtf().subscribeOn(Schedulers.io());
    }

    /**
     * Получение из базы данных списка сущностей {@link TF}
     *
     * @return список всех сущностей {@link TF} в базе данных
     */
    public Single<List<TF>> getTFList(int idOTF) {
        return tfDao.getTfByOtf(idOTF).subscribeOn(Schedulers.io());
    }

    /**
     * Получение из базы данных списка сущностей {@link TD}
     *
     * @return список всех сущностей {@link TD} в базе данных
     */
    public Single<List<TD>> getTDList(int idTF) {
        return tdDao.getTdByTf(idTF).subscribeOn(Schedulers.io());
    }

    /**
     * Получение из базы данных списка сущностей {@link Category}
     *
     * @return список всех сущностей {@link Category} в базе данных
     */
    public Single<List<Category>> getListCategory() {
        return categoryDao.getAllCategories().subscribeOn(Schedulers.io());
    }

    /**
     * Получение из базы данных списка сущностей {@link Group}
     *
     * @return список всех сущностей {@link Group} в базе данных
     */
    public Single<List<Group>> getListGroups() {
        return groupDao.getAllGroups().subscribeOn(Schedulers.io());
    }

    /**
     * Получение из базы данных списка сущностей {@link WorkForm}
     *
     * @return список всех сущностей {@link WorkForm} в базе данных
     */
    public Single<List<WorkForm>> getListWorkForms() {
        return workFormDao.getAllWorkForms().subscribeOn(Schedulers.io());
    }

    /**
     * Добавление новой строки {@link Journal} в базу данных
     *
     * @param journal заполненная строка, вставляемая в БД в таблицу {@link Journal}
     */
    public Completable insertItemJournal(Journal journal) {
        return journalDao.insert(journal).subscribeOn(Schedulers.io());
    }

    /**
     * Удаление из базы данных строки {@link Journal}
     *
     * @param journal удаляемая строка таблицы {@link Journal}
     */
    public Completable deleteItemJournal(Journal journal) {
        return journalDao.delete(journal).subscribeOn(Schedulers.io());
    }

    /**
     * Редактирование строки {@link Journal}
     *
     * @param journal обновленная строка таблицы {@link Journal}
     */
    public Completable updateItemJournal(Journal journal) {
        return journalDao.update(journal).subscribeOn(Schedulers.io());
    }

    /**
     * Получить из базы данных строчки таблицы {@link Journal} по id
     *
     * @param id идентификатор возвращаемой строки
     * @return строка в виде объекта
     */
    public Single<Journal> getItemJournal(int id) {
        return journalDao.getItemJournal(id).subscribeOn(Schedulers.io());
    }

    /**
     * Добавление новой строки {@link OTF} в базу данных
     *
     * @param otf заполненная строка, вставляемая в БД в таблицу {@link OTF}
     */
    public Completable insertItemOTF(OTF otf) {
        return otfDao.insert(otf).subscribeOn(Schedulers.io());
    }

    /**
     * Удаление из базы данных строки {@link OTF}
     *
     * @param otf удаляемая строка таблицы {@link OTF}
     */
    public Completable deleteItemOTF(OTF otf) {
        return otfDao.delete(otf).subscribeOn(Schedulers.io());
    }

    /**
     * Редактирование строки {@link OTF}
     *
     * @param otf обновленная строка таблицы {@link OTF}
     */
    public Completable updateItemOTF(OTF otf) {
        return otfDao.update(otf).subscribeOn(Schedulers.io());
    }

    /**
     * Получить из базы данных строчки таблицы {@link OTF} по id
     *
     * @param id идентификатор возвращаемой строки
     * @return строка в виде объекта
     */
    public Single<OTF> getItemOTF(int id) {
        return otfDao.getItemOtf(id).subscribeOn(Schedulers.io());
    }

    /**
     * Добавление новой строки {@link TF} в базу данных
     *
     * @param tf заполненная строка, вставляемая в БД в таблицу {@link TF}
     */
    public Completable insertItemTF(TF tf) {
        return tfDao.insert(tf).subscribeOn(Schedulers.io());
    }

    /**
     * Удаление из базы данных строки {@link TF}
     *
     * @param tf удаляемая строка таблицы {@link TF}
     */
    public Completable deleteItemTF(TF tf) {
        return tfDao.delete(tf).subscribeOn(Schedulers.io());
    }

    /**
     * Редактирование строки {@link TF}
     *
     * @param tf обновленная строка таблицы {@link TF}
     */
    public Completable updateItemTF(TF tf) {
        return tfDao.update(tf).subscribeOn(Schedulers.io());
    }

    /**
     * Получить из базы данных строчки таблицы {@link TF} по id
     *
     * @param id идентификатор возвращаемой строки
     * @return строка в виде объекта
     */
    public Single<TF> getItemTF(int id) {
        return tfDao.getItemTF(id).subscribeOn(Schedulers.io());
    }

    /**
     * Добавление новой строки {@link TD} в базу данных
     *
     * @param td заполненная строка, вставляемая в БД в таблицу {@link TD}
     */
    public Completable insertItemTD(TD td) {
        return tdDao.update(td).subscribeOn(Schedulers.io());
    }

    /**
     * Удаление из базы данных строки {@link TD}
     *
     * @param td удаляемая строка таблицы {@link TD}
     */
    public Completable deleteItemTD(TD td) {
        return tdDao.delete(td).subscribeOn(Schedulers.io());
    }

    /**
     * Редактирование строки {@link TD}
     *
     * @param td обновленная строка таблицы {@link TD}
     */
    public Completable updateItemTD(TD td) {
        return tdDao.update(td).subscribeOn(Schedulers.io());
    }

    /**
     * Получить из базы данных строчки таблицы {@link TD} по id
     *
     * @param id идентификатор возвращаемой строки
     * @return строка в виде объекта
     */
    public Single<TD> getItemTD(int id) {
        return tdDao.getItemTD(id).subscribeOn(Schedulers.io());
    }

    /**
     * Получить из БД строку таблицы {@link TD} по коду
     *
     * @param code код возвращаемой строки (код в виде пустой строки = "Иная деятельность")
     * @return строка в виде объекта
     */
    public Single<TD> getTdByCode(String code) {
        return tdDao.getTdByCode(code).subscribeOn(Schedulers.io());
    }

    /**
     * Добавление новой строки {@link Category} в базу данных
     *
     * @param category заполненная строка, вставляемая в БД в таблицу {@link Category}
     */
    public Completable insertItemCategory(Category category) {
        return categoryDao.insert(category).subscribeOn(Schedulers.io());
    }

    /**
     * Удаление из базы данных строки {@link Category}
     *
     * @param category удаляемая строка таблицы {@link Category}
     */
    public Completable deleteItemCategory(Category category) {
        return categoryDao.delete(category).subscribeOn(Schedulers.io());
    }

    /**
     * Редактирование строки {@link Category}
     *
     * @param category обновленная строка таблицы {@link Category}
     */
    public Completable updateItemCategory(Category category) {
        return categoryDao.update(category).subscribeOn(Schedulers.io());
    }

    /**
     * Получить из базы данных строчки таблицы {@link Category} по id
     *
     * @param id идентификатор возвращаемой строки
     * @return строка в виде объекта
     */
    public Single<Category> getItemCategory(int id) {
        return categoryDao.getItemCategory(id).subscribeOn(Schedulers.io());
    }

    /**
     * Добавление новой строки {@link Group} в базу данных
     *
     * @param group заполненная строка, вставляемая в БД в таблицу {@link Group}
     */
    public Completable insertItemGroup(Group group) {
        return groupDao.insert(group).subscribeOn(Schedulers.io());
    }

    /**
     * Удаление из базы данных строки {@link Group}
     *
     * @param group удаляемая строка таблицы {@link Group}
     */
    public Completable deleteItemGroup(Group group) {
        return groupDao.delete(group).subscribeOn(Schedulers.io());
    }

    /**
     * Редактирование строки {@link Group}
     *
     * @param group обновленная строка таблицы {@link Group}
     */
    public Completable updateItemGroup(Group group) {
        return groupDao.update(group).subscribeOn(Schedulers.io());
    }

    /**
     * Получить из базы данных строчки таблицы {@link Group} по id
     *
     * @param id идентификатор возвращаемой строки
     * @return строка в виде объекта
     */
    public Single<Group> getItemGroup(int id) {
        return groupDao.getItemGroup(id).subscribeOn(Schedulers.io());
    }

    /**
     * Добавление новой строки {@link WorkForm} в базу данных
     *
     * @param workForm заполненная строка, вставляемая в БД в таблицу {@link WorkForm}
     */
    public Completable insertItemWorkForm(WorkForm workForm) {
        return workFormDao.insert(workForm).subscribeOn(Schedulers.io());
    }

    /**
     * Удаление из базы данных строки {@link WorkForm}
     *
     * @param workForm удаляемая строка таблицы {@link WorkForm}
     */
    public Completable deleteItemWorkForm(WorkForm workForm) {
        return workFormDao.delete(workForm).subscribeOn(Schedulers.io());
    }

    /**
     * Редактирование строки {@link WorkForm}
     *
     * @param workForm обновленная строка таблицы {@link WorkForm}
     */
    public Completable updateItemWorkForm(WorkForm workForm) {
        return workFormDao.update(workForm).subscribeOn(Schedulers.io());
    }

    /**
     * Получить из базы данных строчки таблицы {@link WorkForm} по id
     *
     * @param id идентификатор возвращаемой строки
     * @return строка в виде объекта
     */
    public Single<WorkForm> getItemWorkForm(int id) {
        return workFormDao.getItemWorkForm(id).subscribeOn(Schedulers.io());
    }

    /**
     * Метод получения из БД строк таблицы {@link Journal}, отфильтрованных по выбранной ОТФ и периоду
     *
     * @param idOTF    id выбранной ОТФ
     * @param dateFrom Начало периода отчета
     * @param dateTo   Конец периода отчета
     * @return список объектов {@link Journal}
     */
    public Single<List<ReportData>> getReport(int idOTF, long dateFrom, long dateTo) {
        return reportDao.getReport(idOTF, dateFrom, dateTo).subscribeOn(Schedulers.io());
    }

    /**
     * Получить из таблицы {@link Journal} список всех заполненных ФИО
     *
     * @return список ФИО из таблицы БД {@link Journal}
     */
    public Single<List<String>> getListFullNames() {
        return journalDao.getListFullNames().subscribeOn(Schedulers.io());
    }

    /**
     * Метод получения из БД списка {@link ReportingJournal}
     * с подставлением нужных значений из сущностей:
     * {@link Journal}
     * {@link Category}
     * {@link Group}
     * {@link WorkForm}
     *
     * @return список {@link ReportingJournal} (нужно перевести в дополнительный поток)
     */
    public Single<List<ReportingJournal>> getListReportingJournal() {
        return Single.wrap(observer -> observer.onSuccess(prepareListReportingJournal()));
    }

    private List<ReportingJournal> prepareListReportingJournal() {
        List<ReportingJournal> reportingJournalList = new ArrayList<>();
        List<Journal> journalList = new ArrayList<>(journalDao.getAllSimple());
        for (Journal j : journalList) {
            reportingJournalList.add(mappingReportingJournal(j));
        }
        return reportingJournalList;
    }

    private ReportingJournal mappingReportingJournal(Journal journal) {
        ReportingJournal reportingJournal = new ReportingJournal();
        //методы расположены в таком порядке, в котором они представлены на экране пользователя
        reportingJournal.setDate(journal.getDate());
        reportingJournal.setQuantityPeople(journal.getQuantityPeople());
        reportingJournal.setWorkTime(journal.getWorkTime());
        reportingJournal.setNameCategory(mappingNameCategory(journal));
        reportingJournal.setNameGroup(mappingNameGroup(journal));
        reportingJournal.setName(journal.getName());
        reportingJournal.setDeclaredRequest(journal.getDeclaredRequest());
        reportingJournal.setRealRequest(journal.getRealRequest());
        reportingJournal.setNameWorkForm(mappingWorkForm(journal));
        reportingJournal.setCodeTd(journal.getCodeTd());
        reportingJournal.setComment(journal.getComment());
        return reportingJournal;
    }

    private String mappingNameCategory(Journal journal) {
        int id = journal.getIdCategory();
        if (id != 0) {
            return categoryDao.getItemCategorySimple(id).getName();
        } else {
            return null;
        }
    }

    private String mappingNameGroup(Journal journal) {
        int id = journal.getIdGroup();
        if (id != 0) {
            return groupDao.getItemGroupSimple(id).getName();
        } else {
            return null;
        }
    }

    private String mappingWorkForm(Journal journal) {
        int id = journal.getIdWorkForm();
        if (id != 0) {
            return workFormDao.getItemWorkFormSimple(id).getName();
        } else {
            return null;
        }
    }



}
