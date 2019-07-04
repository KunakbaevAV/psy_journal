package ru.geekbrains.psy_journal.model.database;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.model.data.Category;
import ru.geekbrains.psy_journal.model.data.Group;
import ru.geekbrains.psy_journal.model.data.Journal;
import ru.geekbrains.psy_journal.model.data.OTF;
import ru.geekbrains.psy_journal.model.data.ReportData;
import ru.geekbrains.psy_journal.model.data.TD;
import ru.geekbrains.psy_journal.model.data.TF;
import ru.geekbrains.psy_journal.model.data.WorkForm;
import ru.geekbrains.psy_journal.model.database.dao.CategoryDao;
import ru.geekbrains.psy_journal.model.database.dao.GroupDao;
import ru.geekbrains.psy_journal.model.database.dao.JournalDao;
import ru.geekbrains.psy_journal.model.database.dao.OTFDao;
import ru.geekbrains.psy_journal.model.database.dao.ReportDao;
import ru.geekbrains.psy_journal.model.database.dao.TDDao;
import ru.geekbrains.psy_journal.model.database.dao.TFDao;
import ru.geekbrains.psy_journal.model.database.dao.WorkFormDao;

import static ru.geekbrains.psy_journal.Constants.DB_ADD_ERROR;
import static ru.geekbrains.psy_journal.Constants.DB_ADD_GOOD;
import static ru.geekbrains.psy_journal.Constants.DB_LOGS;

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
    void initializeOTF(List<OTF> list) {
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
    void initializeTF(List<TF> list) {
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
    void initializeTD(List<TD> list) {
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
    void initializeCategory(List<Category> list) {
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
    void initializeGroup(List<Group> list) {
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
    void initializeWorkForms(List<WorkForm> list) {
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
     * @return id вставленной строки
     */
    public Single<Long> insertItemJournal(Journal journal) {
        return journalDao.insert(journal).subscribeOn(Schedulers.io());
    }

    /**
     * Удаление из базы данных строки {@link Journal}
     *
     * @param journal удаляемая строка таблицы {@link Journal}
     * @return id удаленной строки
     */
    public Single<Integer> deleteItemJournal(Journal journal) {
        return journalDao.delete(journal).subscribeOn(Schedulers.io());
    }

    /**
     * Редактирование строки {@link Journal}
     *
     * @param journal обновленная строка таблицы {@link Journal}
     * @return id обновленной строки
     */
    public Single<Integer> updateItemJournal(Journal journal) {
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
     * @return id вставленной строки
     */
    public Single<Long> insertItemOTF(OTF otf) {
        return otfDao.insert(otf).subscribeOn(Schedulers.io());
    }

    /**
     * Удаление из базы данных строки {@link OTF}
     *
     * @param otf удаляемая строка таблицы {@link OTF}
     * @return id удаленной строки
     */
    public Single<Integer> deleteItemOTF(OTF otf) {
        return otfDao.delete(otf).subscribeOn(Schedulers.io());
    }

    /**
     * Редактирование строки {@link OTF}
     *
     * @param otf обновленная строка таблицы {@link OTF}
     * @return id обновленной строки
     */
    public Single<Integer> updateItemOTF(OTF otf) {
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
     * @return id вставленной строки
     */
    public Single<Long> insertItemTF(TF tf) {
        return tfDao.insert(tf).subscribeOn(Schedulers.io());
    }

    /**
     * Удаление из базы данных строки {@link TF}
     *
     * @param tf удаляемая строка таблицы {@link TF}
     * @return id удаленной строки
     */
    public Single<Integer> deleteItemTF(TF tf) {
        return tfDao.delete(tf).subscribeOn(Schedulers.io());
    }

    /**
     * Редактирование строки {@link TF}
     *
     * @param tf обновленная строка таблицы {@link TF}
     * @return id обновленной строки
     */
    public Single<Integer> updateItemTF(TF tf) {
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
     * @return id вставленной строки
     */
    public Single<Integer> insertItemTD(TD td) {
        return tdDao.update(td).subscribeOn(Schedulers.io());
    }

    /**
     * Удаление из базы данных строки {@link TD}
     *
     * @param td удаляемая строка таблицы {@link TD}
     * @return id удаленной строки
     */
    public Single<Integer> deleteItemTD(TD td) {
        return tdDao.delete(td).subscribeOn(Schedulers.io());
    }

    /**
     * Редактирование строки {@link TD}
     *
     * @param td обновленная строка таблицы {@link TD}
     * @return id обновленной строки
     */
    public Single<Integer> updateItemTD(TD td) {
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
    public Single<Long> insertItemCategory(Category category) {
        return categoryDao.insert(category).subscribeOn(Schedulers.io());
        /*categoryDao.insert(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> Log.i(DB_LOGS, "insertItemCategory: " + DB_ADD_GOOD),
                        er -> Log.e(DB_LOGS, "insertItemCategory: " + DB_ADD_ERROR, er)
                ).isDisposed();*/
    }

    /**
     * Удаление из базы данных строки {@link Category}
     *
     * @param category удаляемая строка таблицы {@link Category}
     * @return id удаленной строки
     */
    public Single<Integer> deleteItemCategory(Category category) {
        return categoryDao.delete(category).subscribeOn(Schedulers.io());
    }

    /**
     * Редактирование строки {@link Category}
     *
     * @param category обновленная строка таблицы {@link Category}
     * @return id обновленной строки
     */
    public Single<Integer> updateItemCategory(Category category) {
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
     * @return id вставленной строки
     */
    public Single<Long> insertItemGroup(Group group) {
        return groupDao.insert(group).subscribeOn(Schedulers.io());
    }

    /**
     * Удаление из базы данных строки {@link Group}
     *
     * @param group удаляемая строка таблицы {@link Group}
     * @return id удаленной строки
     */
    public Single<Integer> deleteItemGroup(Group group) {
        return groupDao.delete(group).subscribeOn(Schedulers.io());
    }

    /**
     * Редактирование строки {@link Group}
     *
     * @param group обновленная строка таблицы {@link Group}
     * @return id обновленной строки
     */
    public Single<Integer> updateItemGroup(Group group) {
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
     * @return id вставленной строки
     */
    public Single<Long> insertItemWorkForm(WorkForm workForm) {
        return workFormDao.insert(workForm).subscribeOn(Schedulers.io());
    }

    /**
     * Удаление из базы данных строки {@link WorkForm}
     *
     * @param workForm удаляемая строка таблицы {@link WorkForm}
     * @return id удаленной строки
     */
    public Single<Integer> deleteItemWorkForm(WorkForm workForm) {
        return workFormDao.delete(workForm).subscribeOn(Schedulers.io());
    }

    /**
     * Редактирование строки {@link WorkForm}
     *
     * @param workForm обновленная строка таблицы {@link WorkForm}
     * @return id обновленной строки
     */
    public Single<Integer> updateItemWorkForm(WorkForm workForm) {
        return workFormDao.update(workForm).subscribeOn(Schedulers.io());
    }

    /**
     * Получить из базы данных строчки таблицы {@link WorkForm} по id
     *
     * @param id идентификатор возвращаемой строки
     * @return строка в виде объекта
     */
    public Single<WorkForm> getItemWorkForm(int id) {
        return workFormDao.getItemWorkWorm(id).subscribeOn(Schedulers.io());
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

}
