package ru.geekbrains.psy_journal.model.database;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
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

    //первичная загрузка из файла в базу OTF
    void initializeOTF(List<OTF> list) {
        Disposable disposable = otfDao.insert(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Log.i("initializeOTF: ", "added to base"),
                        e -> Log.e("initializeOTF: ", String.format("error adding to database, %s", e.getMessage())));
    }

    //первичная загрузка из файла в базу TF
    void initializeTF(List<TF> list) {
        Disposable disposable = tfDao.insert(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Log.i("initializeTF: ", "added to base"),
                        e -> Log.e("initializeTF: ", String.format("error adding to database, %s", e.getMessage())));
    }

    //первичная загрузка из файла в базу TD
    void initializeTD(List<TD> list) {
        Disposable disposable = tdDao.insert(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Log.i("initializeTD: ", "added to base"),
                        e -> Log.e("initializeTD: ", String.format("error adding to database, %s", e.getMessage())));
    }

    void initializeCategory(List<Category> list) {
        Disposable disposable = categoryDao.insertList(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Log.i("initializeCategory: ", "added to base"),
                        e -> Log.e("initializeCategory: ", String.format("error adding to database, %s", e.getMessage())));
    }

    void initializeGroup(List<Group> list) {
        Disposable disposable = groupDao.insertList(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Log.i("initializeGroup: ", "added to base"),
                        e -> Log.e("initializeGroup: ", String.format("error adding to database, %s", e.getMessage())));
    }

    void initializeWorkForms(List<WorkForm> list) {
        Disposable disposable = workFormDao.insertList(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Log.i("initializeWorkForm: ", "added to base"),
                        e -> Log.e("initializeWorkForm: ", String.format("error adding to database, %s", e.getMessage())));

    }

    /**
     * @return Возвращает список всех зарегистрированных единиц работы в обертке {@link Single}
     */
    public Single<List<Journal>> getJournalList() {
        return Single.create((SingleOnSubscribe<List<Journal>>)
                emitter -> {
                    List<Journal> list = journalDao.getAll();
                    emitter.onSuccess(list);
                }).subscribeOn(Schedulers.io());
    }

    /**
     * @return Возвращает список Обобщенных трудовых функций в обертке {@link Single}
     */
    public Single<List<OTF>> getOTFList() {
        return Single.create((SingleOnSubscribe<List<OTF>>)
                emitter -> {
                    List<OTF> list = otfDao.getAllOtf();
                    emitter.onSuccess(list);
                }).subscribeOn(Schedulers.io());
    }

    /**
     * @param idOTF id Обобщенной трудовой функции, к которой относится список возвращаемых Трудовых функций
     * @return Возвращает список Трудовых функций, относящихся к указанной Обобщенной трудовой функции в обертке {@link Single}
     */
    public Single<List<TF>> getTFList(int idOTF) {
        return Single.create((SingleOnSubscribe<List<TF>>)
                emitter -> {
                    List<TF> list = tfDao.getTfByOtf(idOTF);
                    emitter.onSuccess(list);
                }).subscribeOn(Schedulers.io());
    }

    /**
     * @param idTF id Трудовой функции, к которой относятся возвращаемые Трудовые действия
     * @return Возвращает список Трудовых действий, относящихся к указанной Трудовой функции в обертке {@link Single}
     */
    public Single<List<TD>> getTDList(int idTF) {
        return Single.create((SingleOnSubscribe<List<TD>>)
                emitter -> {
                    List<TD> list = tdDao.getTdByTf(idTF);
                    emitter.onSuccess(list);
                }).subscribeOn(Schedulers.io());
    }

    /**
     * @return Возвращает список категорий людей, с которыми работает пользователь в обертке {@link Single}
     */
    public Single<List<Category>> getListCategory() {
        return Single.create((SingleOnSubscribe<List<Category>>)
                emitter -> {
                    List<Category> list = categoryDao.getAllCategories();
                    emitter.onSuccess(list);
                }
        ).subscribeOn(Schedulers.io());
    }

    /**
     * @return Возвращает список групп(классов), с которыми работает пользователь в обертке {@link Single}
     */
    public Single<List<Group>> getListGroups() {
        return Single.create((SingleOnSubscribe<List<Group>>)
                emitter -> {
                    List<Group> list = groupDao.getAllGroups();
                    emitter.onSuccess(list);
                }
        ).subscribeOn(Schedulers.io());
    }

    /**
     * @return Возвращает список форм работы пользователя в обертке {@link Single}
     */
    public Single<List<WorkForm>> getListWorkForms() {
        return Single.create((SingleOnSubscribe<List<WorkForm>>)
                emitter -> {
                    List<WorkForm> list = workFormDao.getAllWorkForms();
                    emitter.onSuccess(list);
                }
        ).subscribeOn(Schedulers.io());
    }

    /**
     * @param journal Заполненная строчка, вставляемая в БД в таблицу {@link Journal}
     * @return Возвращает id вставленной строчки в обертке {@link Single}
     */
    public Single<Long> insertItemJournal(Journal journal) {
        return Single.create((SingleOnSubscribe<Long>)
                emitter -> {
                    long id = journalDao.insert(journal);
                    emitter.onSuccess(id);
                }
        ).subscribeOn(Schedulers.io());
    }

    /**
     * @param journal Удаляемая строка таблицы {@link Journal}
     * @return id удаленной строчки в обертке {@link Single}
     */
    public Single<Integer> deleteItemJournal(Journal journal) {
        return Single.create((SingleOnSubscribe<Integer>)
                emitter -> {
                    int id = journalDao.delete(journal);
                    emitter.onSuccess(id);
                }
        ).subscribeOn(Schedulers.io());
    }

    /**
     * @param journal Обновленная строчка таблицы {@link Journal}
     * @return id обновленной строчки в обертке {@link Single}
     */
    public Single<Integer> updateItemJournal(Journal journal) {
        return Single.create((SingleOnSubscribe<Integer>)
                emitter -> {
                    int id = journalDao.update(journal);
                    emitter.onSuccess(id);
                }).subscribeOn(Schedulers.io());
    }

    /**
     * Метод получения из БД строчки таблицы {@link Journal} по id, обернутый в {@link Single}
     *
     * @param id идентификатор возвращаемой строки
     * @return строка в виде объекта
     */
    public Single<Journal> getItemJournal(int id) {
        return Single.create((SingleOnSubscribe<Journal>)
                emitter -> {
                    Journal item = journalDao.getItemJournal(id);
                    emitter.onSuccess(item);
                }).subscribeOn(Schedulers.io());
    }

    /**
     * @param otf Вставляемая Обобщенная трудовая функция
     * @return id вставленной {@link OTF} в обертке {@link Single}
     */
    public Single<Long> insertItemOTF(OTF otf) {
        return Single.create((SingleOnSubscribe<Long>)
                emitter -> {
                    long id = otfDao.insert(otf);
                    emitter.onSuccess(id);
                }).subscribeOn(Schedulers.io());
    }

    /**
     * @param otf Удаляемая Обобщенная трудовая функция
     * @return id удаленной {@link OTF} в обертке {@link Single}
     */
    public Single<Integer> deleteItemOTF(OTF otf) {
        return Single.create((SingleOnSubscribe<Integer>)
                emitter -> {
                    int id = otfDao.delete(otf);
                    emitter.onSuccess(id);
                }).subscribeOn(Schedulers.io());
    }

    public Single<Integer> updateItemOTF(OTF otf) {
        return Single.create((SingleOnSubscribe<Integer>)
                emitter -> {
                    int id = otfDao.update(otf);
                    emitter.onSuccess(id);
                }).subscribeOn(Schedulers.io());
    }

    /**
     * Метод получения из БД строчки таблицы {@link OTF} по id, обернутый в {@link Single}
     *
     * @param id идентификатор возвращаемой строки
     * @return строка в виде объекта
     */
    public Single<OTF> getItemOTF(int id) {
        return Single.create((SingleOnSubscribe<OTF>)
                emitter -> {
                    OTF item = otfDao.getItemOtf(id);
                    emitter.onSuccess(item);
                }).subscribeOn(Schedulers.io());
    }

    public Single<Long> insertItemTF(TF tf) {
        return Single.create((SingleOnSubscribe<Long>)
                emitter -> {
                    long id = tfDao.insert(tf);
                    emitter.onSuccess(id);
                }).subscribeOn(Schedulers.io());
    }

    public Single<Integer> deleteItemTF(TF tf) {
        return Single.create((SingleOnSubscribe<Integer>)
                emitter -> {
                    int id = tfDao.delete(tf);
                    emitter.onSuccess(id);
                }).subscribeOn(Schedulers.io());
    }

    public Single<Integer> updateItemTF(TF tf) {
        return Single.create((SingleOnSubscribe<Integer>)
                emitter -> {
                    int id = tfDao.update(tf);
                    emitter.onSuccess(id);
                }).subscribeOn(Schedulers.io());
    }

    /**
     * Метод получения из БД строчки таблицы {@link TF} по id, обернутый в {@link Single}
     *
     * @param id идентификатор возвращаемой строки
     * @return строка в виде объекта
     */
    public Single<TF> getItemTF(int id) {
        return Single.create((SingleOnSubscribe<TF>)
                emitter -> {
                    TF item = tfDao.getItemTF(id);
                    emitter.onSuccess(item);
                }).subscribeOn(Schedulers.io());
    }

    public Single<Long> insertItemTD(TD td) {
        return Single.create((SingleOnSubscribe<Long>)
                emitter -> {
                    long id = tdDao.update(td);
                    emitter.onSuccess(id);
                }).subscribeOn(Schedulers.io());
    }

    public Single<Integer> deleteItemTD(TD td) {
        return Single.create((SingleOnSubscribe<Integer>)
                emitter -> {
                    int id = tdDao.delete(td);
                    emitter.onSuccess(id);
                }).subscribeOn(Schedulers.io());
    }

    public Single<Integer> updateItemTD(TD td) {
        return Single.create((SingleOnSubscribe<Integer>)
                emitter -> {
                    int id = tdDao.update(td);
                    emitter.onSuccess(id);
                }).subscribeOn(Schedulers.io());
    }

    /**
     * Метод получения из БД строчки таблицы {@link TD} по id, обернутый в {@link Single}
     *
     * @param id идентификатор возвращаемой строки
     * @return строка в виде объекта
     */
    public Single<TD> getItemTD(int id) {
        return Single.create((SingleOnSubscribe<TD>)
                emitter -> {
                    TD item = tdDao.getItemTD(id);
                    emitter.onSuccess(item);
                }).subscribeOn(Schedulers.io());
    }

    /**
     * Метод получения из БД строчки таблицы {@link TD} по коду, обернутый в {@link Single}
     *
     * @param code код возвращаемой строки (код в виде пустой строки = "Иная деятельность")
     * @return строка в виде объекта
     */
    public Single<TD> getTdByCode(String code) {
        return Single.create((SingleOnSubscribe<TD>)
                emitter -> {
                    TD item = tdDao.getTdByCode(code);
                    emitter.onSuccess(item);
                }).subscribeOn(Schedulers.io());
    }

    /**
     * Метод вставки новой Категории в БД, обернутый в {@link Single}
     *
     * @param category Вставляемая Категория
     * @return id строки, вставленной в БД в таблицу {@link Category}
     */
    public Single<Long> insertItemCategory(Category category) {
        return Single.create((SingleOnSubscribe<Long>)
                emitter -> {
                    long id = categoryDao.insert(category);
                    emitter.onSuccess(id);
                }).subscribeOn(Schedulers.io());
    }

    public Single<Integer> deleteItemCategory(Category category) {
        return Single.create((SingleOnSubscribe<Integer>)
                emitter -> {
                    int id = categoryDao.delete(category);
                    emitter.onSuccess(id);
                }).subscribeOn(Schedulers.io());
    }

    public Single<Integer> updateItemCategory(Category category) {
        return Single.create((SingleOnSubscribe<Integer>)
                emitter -> {
                    int id = categoryDao.update(category);
                    emitter.onSuccess(id);
                }).subscribeOn(Schedulers.io());
    }

    /**
     * Метод получения из БД строчки таблицы {@link Category} по id, обернутый в {@link Single}
     *
     * @param id идентификатор возвращаемой строки
     * @return строка в виде объекта
     */
    public Single<Category> getItemCategory(int id) {
        return Single.create((SingleOnSubscribe<Category>)
                emitter -> {
                    Category item = categoryDao.getItemCategory(id);
                    emitter.onSuccess(item);
                }).subscribeOn(Schedulers.io());
    }

    /**
     * Метод вставки новой Группы в БД, обернутый в {@link Single}
     *
     * @param group Вставляемая Группа
     * @return id строки, вставленной в БД в таблицу {@link Group}
     */
    public Single<Long> insertItemGroup(Group group) {
        return Single.create((SingleOnSubscribe<Long>)
                emitter -> {
                    long id = groupDao.insert(group);
                    emitter.onSuccess(id);
                }).subscribeOn(Schedulers.io());
    }

    public Single<Integer> deleteItemGroup(Group group) {
        return Single.create((SingleOnSubscribe<Integer>)
                emitter -> {
                    int id = groupDao.delete(group);
                    emitter.onSuccess(id);
                }).subscribeOn(Schedulers.io());
    }

    public Single<Integer> updateItemGroup(Group group) {
        return Single.create((SingleOnSubscribe<Integer>)
                emitter -> {
                    int id = groupDao.update(group);
                    emitter.onSuccess(id);
                }).subscribeOn(Schedulers.io());
    }

    /**
     * Метод получения из БД строчки таблицы {@link Group} по id, обернутый в {@link Single}
     *
     * @param id идентификатор возвращаемой строки
     * @return строка в виде объекта
     */
    public Single<Group> getItemGroup(int id) {
        return Single.create((SingleOnSubscribe<Group>)
                emitter -> {
                    Group item = groupDao.getItemGroup(id);
                    emitter.onSuccess(item);
                }).subscribeOn(Schedulers.io());
    }

    /**
     * Метод вставки новой Формы работы в БД, обернутый в {@link Single}
     *
     * @param workForm Вставляемая форма работы
     * @return id строки вставленной в БД в таблицу {@link WorkForm}
     */
    public Single<Long> insertItemWorkForm(WorkForm workForm) {
        return Single.create((SingleOnSubscribe<Long>)
                emitter -> {
                    long id = workFormDao.insert(workForm);
                    emitter.onSuccess(id);
                }).subscribeOn(Schedulers.io());
    }

    public Single<Integer> deleteItemWorkForm(WorkForm workForm) {
        return Single.create((SingleOnSubscribe<Integer>)
                emitter -> {
                    int id = workFormDao.delete(workForm);
                    emitter.onSuccess(id);
                }).subscribeOn(Schedulers.io());
    }

    public Single<Integer> updateItemWorkForm(WorkForm workForm) {
        return Single.create((SingleOnSubscribe<Integer>)
                emitter -> {
                    int id = workFormDao.update(workForm);
                    emitter.onSuccess(id);
                }).subscribeOn(Schedulers.io());
    }

    /**
     * Метод получения из БД строчки таблицы {@link WorkForm} по id, обернутый в {@link Single}
     *
     * @param id идентификатор возвращаемой строки
     * @return строка в виде объекта
     */
    public Single<WorkForm> getItemWorkForm(int id) {
        return Single.create((SingleOnSubscribe<WorkForm>)
                emitter -> {
                    WorkForm item = workFormDao.getItemWorkWorm(id);
                    emitter.onSuccess(item);
                }).subscribeOn(Schedulers.io());
    }

    public Single<List<ReportData>> getReport(int idOTF, long dateFrom, long dateTo) {
        ReportHelper reportHelper = new ReportHelper(tfDao, reportDao);
        return Single.create((SingleOnSubscribe<List<ReportData>>)
                emitter -> {
                    List<ReportData> list = reportHelper.getReportData(idOTF, dateFrom, dateTo);
                    emitter.onSuccess(list);
                }).subscribeOn(Schedulers.io());
    }

    /**
     * Метод получения из БД строк таблицы {@link Journal}, отфильтрованных по выбранной ОТФ и периоду
     *
     * @param idOTF    id выбранной ОТФ
     * @param dateFrom Начало периода отчета
     * @param dateTo   Конец периода отчета
     * @return Возвращает список объектов {@link Journal} в обертке {@link Single}
     */
    public Single<List<Journal>> getLaborFunctionReport(int idOTF, long dateFrom, long dateTo) {
        return journalDao.getLaborFunctionReport(idOTF, dateFrom, dateTo).subscribeOn(Schedulers.io());
    }

    /**
     * Метод получения из таблицы {@link Journal} всех заполненных ФИО
     *
     * @return Возвращает список ФИО из таблицы БД {@link Journal} в обертке {@link Single}
     */
    public Single<List<String>> getListFullNames() {
        return journalDao.getListFullNames().subscribeOn(Schedulers.io());
    }

}
