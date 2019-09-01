package ru.geekbrains.psy_journal.data.repositories;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.psy_journal.data.database.dao.AddedCatalogDao;
import ru.geekbrains.psy_journal.data.database.dao.CategoryDao;
import ru.geekbrains.psy_journal.data.database.dao.GroupDao;
import ru.geekbrains.psy_journal.data.database.dao.JournalDao;
import ru.geekbrains.psy_journal.data.database.dao.OTFDao;
import ru.geekbrains.psy_journal.data.database.dao.ReportDao;
import ru.geekbrains.psy_journal.data.database.dao.TDDao;
import ru.geekbrains.psy_journal.data.database.dao.TFDao;
import ru.geekbrains.psy_journal.data.database.dao.WorkFormDao;
import ru.geekbrains.psy_journal.data.repositories.model.Category;
import ru.geekbrains.psy_journal.data.repositories.model.Group;
import ru.geekbrains.psy_journal.data.repositories.model.Journal;
import ru.geekbrains.psy_journal.data.repositories.model.OTF;
import ru.geekbrains.psy_journal.data.repositories.model.TD;
import ru.geekbrains.psy_journal.data.repositories.model.TF;
import ru.geekbrains.psy_journal.data.repositories.model.WorkForm;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.domain.models.ReportData;

import static ru.geekbrains.psy_journal.Constants.DB_ADD_ERROR;
import static ru.geekbrains.psy_journal.Constants.DB_ADD_GOOD;
import static ru.geekbrains.psy_journal.Constants.DB_LOGS;
/**
 * Организация работы с базой данный в дополнительном потоке
 */
public class RoomHelper implements
	Loadable,
	StorableCategory,
	StorableGroup,
	StorableWorkForm,
	StorableJournal,
	StorableOTF,
	StorableTF,
	StorableTD{

    @Inject JournalDao journalDao;
    @Inject OTFDao otfDao;
    @Inject TFDao tfDao;
    @Inject TDDao tdDao;
    @Inject CategoryDao categoryDao;
    @Inject GroupDao groupDao;
    @Inject WorkFormDao workFormDao;
    @Inject ReportDao reportDao;
    @Inject AddedCatalogDao addedCatalogDao;

    public RoomHelper() {
        App.getAppComponent().inject(this);
    }

    /**
     * Первичная загрузка в базу данных сущностей {@link OTF}
     *
     * @param list загружаемый список сущностей
     */
    @Override
    public void initializeOTF(List<OTF> list) {
        Disposable disposable = otfDao.insert(list)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    () -> Log.i(DB_LOGS, "initializeOTF: " + DB_ADD_GOOD),
                    er -> Log.e(DB_LOGS, "initializeOTF: " + DB_ADD_ERROR, er)
            );
    }

    /**
     * Первичная загрузка в базу данных сущностей {@link TF}
     *
     * @param list загружаемый список сущностей
     */
    @Override
    public void initializeTF(List<TF> list) {
	    Disposable disposable = tfDao.insert(list)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    () -> Log.i(DB_LOGS, "initializeTF: " + DB_ADD_GOOD),
                    er -> Log.e(DB_LOGS, "initializeTF: " + DB_ADD_ERROR, er)
            );
    }

    /**
     * Первичная загрузка в базу данных сущностей {@link TD}
     *
     * @param list загружаемый список сущностей
     */
    @Override
    public void initializeTD(List<TD> list) {
	    Disposable disposable = tdDao.insert(list)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    () -> Log.i(DB_LOGS, "initializeTD: " + DB_ADD_GOOD),
                    er -> Log.e(DB_LOGS, "initializeTD: " + DB_ADD_ERROR, er)
            );
    }

    /**
     * Первичная загрузка в базу данных сущностей {@link Category}
     *
     * @param list загружаемый список сущностей
     */
    @Override
    public void initializeCategory(List<Category> list) {
	    Disposable disposable = categoryDao.insertList(list)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    () -> Log.i(DB_LOGS, "initializeCategory: " + DB_ADD_GOOD),
                    er -> Log.e(DB_LOGS, "initializeCategory: " + DB_ADD_ERROR, er)
            );
    }

    /**
     * Первичная загрузка в базу данных сущностей {@link Group}
     *
     * @param list загружаемый список сущностей
     */
    @Override
    public void initializeGroup(List<Group> list) {
	    Disposable disposable = groupDao.insertList(list)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    () -> Log.i(DB_LOGS, "initializeGroup: " + DB_ADD_GOOD),
                    er -> Log.e(DB_LOGS, "initializeGroup: " + DB_ADD_ERROR, er)
            );
    }

    /**
     * Первичная загрузка в базу данных сущностей {@link WorkForm}
     *
     * @param list загружаемый список сущностей
     */
    @Override
    public void initializeWorkForms(List<WorkForm> list) {
	    Disposable disposable = workFormDao.insertList(list)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    () -> Log.i(DB_LOGS, "initializeWorkForms: " + DB_ADD_GOOD),
                    er -> Log.e(DB_LOGS, "initializeWorkForms: " + DB_ADD_ERROR, er)
            );
    }

    /**
     * Получение из базы данных списка сущностей {@link Journal}
     *
     * @return список всех сущностей {@link Journal} в базе данных
     */
    @Override
    public Single<List<Journal>> getJournalList() {
        return journalDao.getAll().subscribeOn(Schedulers.io());
    }

    /**
     * Получение из базы данных списка сущностей {@link OTF}
     *
     * @return список всех сущностей {@link OTF} в базе данных
     */
    @Override
    public Single<List<OTF>> getOTFList() {
        return otfDao.getAllOtf().subscribeOn(Schedulers.io());
    }

    /**
     * Получение из базы данных списка сущностей {@link TF}
     *
     * @return список всех сущностей {@link TF} в базе данных
     */
    @Override
    public Single<List<TF>> getTFList(int idOTF) {
        return tfDao.getTfByOtf(idOTF).subscribeOn(Schedulers.io());
    }

    /**
     * Получение из базы данных списка сущностей {@link TD}
     *
     * @return список всех сущностей {@link TD} в базе данных
     */
    @Override
    public Single<List<TD>> getTDList(int idTF) {
        return tdDao.getTdByTf(idTF).subscribeOn(Schedulers.io());
    }

    /**
     * Получение из базы данных списка сущностей {@link Category}
     *
     * @return список всех сущностей {@link Category} в базе данных
     */
    @Override
    public Single<List<Category>> getListCategory() {
        return categoryDao.getAllCategories().subscribeOn(Schedulers.io());
    }

    /**
     * Получение из базы данных списка сущностей {@link Group}
     *
     * @return список всех сущностей {@link Group} в базе данных
     */
    @Override
    public Single<List<Group>> getListGroups() {
        return groupDao.getAllGroups().subscribeOn(Schedulers.io());
    }

    /**
     * Получение из базы данных списка сущностей {@link WorkForm}
     *
     * @return список всех сущностей {@link WorkForm} в базе данных
     */
    @Override
    public Single<List<WorkForm>> getListWorkForms() {
        return workFormDao.getAllWorkForms().subscribeOn(Schedulers.io());
    }

    /**
     * Добавление новой строки {@link Journal} в базу данных
     *
     * @param journal заполненная строка, вставляемая в БД в таблицу {@link Journal}
     */
    @Override
    public Completable insertItemJournal(Journal journal) {
        return journalDao.insert(journal).subscribeOn(Schedulers.io());
    }

    /**
     * Удаление из базы данных строки {@link Journal}
     *
     * @param journal удаляемая строка таблицы {@link Journal}
     */
    @Override
    public Completable deleteItemJournal(Journal journal) {
        return journalDao.delete(journal).subscribeOn(Schedulers.io());
    }

    /**
     * Редактирование строки {@link Journal}
     *
     * @param journal обновленная строка таблицы {@link Journal}
     */
    @Override
    public Completable updateItemJournal(Journal journal) {
        return journalDao.update(journal).subscribeOn(Schedulers.io());
    }

    /**
     * Удаление из базы данных строки {@link Category}
     *
     * @param category удаляемая строка таблицы {@link Category}
     */
    @Override
    public Completable deleteItemCategory(Category category) {
        return categoryDao.delete(category).subscribeOn(Schedulers.io());
    }

    /**
     * Редактирование строки {@link Category}
     *
     * @param category обновленная строка таблицы {@link Category}
     */
    @Override
    public Completable updateItemCategory(Category category) {
        return categoryDao.update(category).subscribeOn(Schedulers.io());
    }

    /**
     * Получить из базы данных строчки таблицы {@link Category} по id
     *
     * @param id идентификатор возвращаемой строки
     * @return строка в виде объекта
     */
    @Override
    public Single<Category> getItemCategory(int id) {
        return categoryDao.getItemCategory(id).subscribeOn(Schedulers.io());
    }

    /**
     * Удаление из базы данных строки {@link Group}
     *
     * @param group удаляемая строка таблицы {@link Group}
     */
    @Override
    public Completable deleteItemGroup(Group group) {
        return groupDao.delete(group).subscribeOn(Schedulers.io());
    }

    /**
     * Редактирование строки {@link Group}
     *
     * @param group обновленная строка таблицы {@link Group}
     */
    @Override
    public Completable updateItemGroup(Group group) {
        return groupDao.update(group).subscribeOn(Schedulers.io());
    }

    /**
     * Получить из базы данных строчки таблицы {@link Group} по id
     *
     * @param id идентификатор возвращаемой строки
     * @return строка в виде объекта
     */
    @Override
    public Single<Group> getItemGroup(int id) {
        return groupDao.getItemGroup(id).subscribeOn(Schedulers.io());
    }

    /**
     * Удаление из базы данных строки {@link WorkForm}
     *
     * @param workForm удаляемая строка таблицы {@link WorkForm}
     */
    @Override
    public Completable deleteItemWorkForm(WorkForm workForm) {
        return workFormDao.delete(workForm).subscribeOn(Schedulers.io());
    }

    /**
     * Редактирование строки {@link WorkForm}
     *
     * @param workForm обновленная строка таблицы {@link WorkForm}
     */
    @Override
    public Completable updateItemWorkForm(WorkForm workForm) {
        return workFormDao.update(workForm).subscribeOn(Schedulers.io());
    }

    /**
     * Получить из базы данных строчки таблицы {@link WorkForm} по id
     *
     * @param id идентификатор возвращаемой строки
     * @return строка в виде объекта
     */
    @Override
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
    @Override
    public Single<List<ReportData>> getReport(int idOTF, long dateFrom, long dateTo) {
        return reportDao.getReport(idOTF, dateFrom, dateTo).subscribeOn(Schedulers.io());
    }

    /**
     * Метод получения из БД строк таблицы {@link Journal}, отфильтрованных по выбранной ТФ и периоду
     *
     * @param codeTF   код выбранной ТФ
     * @param dateFrom Начало периода отчета
     * @param dateTo   Конец периода отчета
     * @return список объектов {@link ReportData}
     */
    @Override
    public Single<List<ReportData>> getReportByTF(String codeTF, long dateFrom, long dateTo) {
        return reportDao.getReportByTF(codeTF, dateFrom, dateTo).subscribeOn(Schedulers.io());
    }

    /**
     * Получить из таблицы {@link Journal} список всех заполненных ФИО
     *
     * @return список ФИО из таблицы БД {@link Journal}
     */
    @Override
    public Single<List<String>> getListFullNames() {
        return journalDao.getListFullNames().subscribeOn(Schedulers.io());
    }

	/**
	 * получение добавленной Категории по name
	 */
	@Override
	public Single<Category> getAddedCategoryItem(String name){
		return Single.fromCallable(() -> addedCatalogDao.getAddedCategoryItem(name))
			.subscribeOn(Schedulers.io());
	}

	/**
	 * получение добавленной Group по name
	 */
	@Override
	public Single<Group> getAddedGroupItem(String name){
		return Single.fromCallable(() -> addedCatalogDao.getAddedGroupItem(name))
			.subscribeOn(Schedulers.io());
	}

	/**
	 * получение добавленной WorkForm по name
	 */
	@Override
	public Single<WorkForm> getAddedWorkFormItem(String name){
		return Single.fromCallable(() -> addedCatalogDao.getAddedWorkFormItem(name))
			.subscribeOn(Schedulers.io());
	}
}




