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
import ru.geekbrains.psy_journal.model.data.TD;
import ru.geekbrains.psy_journal.model.data.TF;
import ru.geekbrains.psy_journal.model.data.WorkForm;
import ru.geekbrains.psy_journal.model.database.dao.CategoryDao;
import ru.geekbrains.psy_journal.model.database.dao.GroupDao;
import ru.geekbrains.psy_journal.model.database.dao.JournalDao;
import ru.geekbrains.psy_journal.model.database.dao.OTFDao;
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

    public RoomHelper() {
        App.getAppComponent().inject(this);
    }

    //первичная загрузка из файла в базу OTF
	public void initializeOTF(List<OTF> list){
		Disposable disposable = otfDao.insert(list)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(() -> Log.i("initializeOTF: ", "added to base"),
				e -> Log.e("initializeOTF: ", String.format("error adding to database, %s", e.getMessage())));
	}

	//первичная загрузка из файла в базу TF
	public void initializeTF(List<TF> list){
		Disposable disposable = tfDao.insert(list)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(() -> Log.i("initializeTF: ", "added to base"),
				e -> Log.e("initializeTF: ", String.format("error adding to database, %s", e.getMessage())));
	}

	//первичная загрузка из файла в базу TD
	public void initializeTD(List<TD> list){
		Disposable disposable = tdDao.insert(list)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(() -> Log.i("initializeTD: ", "added to base"),
				e -> Log.e("initializeTD: ", String.format("error adding to database, %s", e.getMessage())));
	}

    //Возвращает список всех зарегистрированных единиц работы
    public Single<List<Journal>> getJournalList() {
        return journalDao.getAll().subscribeOn(Schedulers.io());
    }

    //Возвращает список Обобщенных трудовых функций
    public Single<List<OTF>> getOTFList() {
        return otfDao.getAllOtf().subscribeOn(Schedulers.io());
    }

    //Возвращает список Трудовых функций, относящихся к указанной Обобщенной трудовой функции
    public Single<List<TF>> getTFList(int idOTF) {
        return tfDao.getTfByOtf(idOTF).subscribeOn(Schedulers.io());
    }

    //Возвращает список Трудовых действий, относящихся к указанной Трудовой функции
    public Single<List<TD>> getTDList(int idTF) {
        return tdDao.getTdByTf(idTF).subscribeOn(Schedulers.io());
    }

    //Возвращает список категорий людей, с которыми работает пользователь
    public Single<List<Category>> getListCategory() {
        return categoryDao.getAllCategories().subscribeOn(Schedulers.io());
    }

    //Возвращает список групп(классов), с которыми работает пользователь
    public Single<List<Group>> getListGroups() {
        return groupDao.getAllGroups().subscribeOn(Schedulers.io());
    }

    //Возвращает список форм работы пользователя
    public Single<List<WorkForm>> getListWorkForms() {
        return workFormDao.getAllWorkForms().subscribeOn(Schedulers.io());
    }

    public Single<Long> insertItemJournal(Journal journal) {
        return Single.create((SingleOnSubscribe<Long>)
                emitter -> journalDao.insert(journal)
        ).subscribeOn(Schedulers.io());
    }

    public int deleteItemJournal(Journal journal) {
        return journalDao.delete(journal);
    }

    public int updateItemJournal(Journal journal) {
        return journalDao.update(journal);
    }

    public Single<Long> insertItemOTF(OTF otf) {
        return Single.create((SingleOnSubscribe<Long>)
                emitter -> otfDao.insert(otf)).subscribeOn(Schedulers.io());
    }

    public Single<Long> insertListOTF(List<OTF> otfList) {
        return Single.create((SingleOnSubscribe<Long>)
                emitter -> {
                    for (OTF item : otfList) {
                        otfDao.insert(item);
                    }
                }
        ).subscribeOn(Schedulers.io());
    }

    public int deleteItemOTF(OTF otf) {
        return otfDao.delete(otf);
    }

    public int updateItemOTF(OTF otf) {
        return otfDao.update(otf);
    }

    public Single<Long> insertItemTF(TF tf) {
        return Single.create((SingleOnSubscribe<Long>)
                emitter -> tfDao.insert(tf)).subscribeOn(Schedulers.io());
    }

    public Single<Long> insertListTF(List<TF> tfList) {
        return Single.create((SingleOnSubscribe<Long>)
                emitter -> {
                    for (TF item : tfList) {
                        tfDao.insert(item);
                    }
                }
        ).subscribeOn(Schedulers.io());
    }

    public int deleteItemTF(TF tf) {
        return tfDao.delete(tf);
    }

    public int updateItemTF(TF tf) {
        return tfDao.update(tf);
    }

    public Single<Long> insertItemTD(TD td) {
        return Single.create((SingleOnSubscribe<Long>)
                emitter -> tdDao.insert(td)).subscribeOn(Schedulers.io());
    }

    public Single<Long> insertListTD(List<TD> tdList) {
        return Single.create((SingleOnSubscribe<Long>)
                emitter -> {
                    for (TD item : tdList) {
                        tdDao.insert(item);
                    }
                }
        ).subscribeOn(Schedulers.io());
    }

    public int deleteItemTD(TD td) {
        return tdDao.delete(td);
    }

    public int updateItemTD(TD td) {
        return tdDao.update(td);
    }

    public Single<Long> insertItemCategory(Category category) {
        return Single.create((SingleOnSubscribe<Long>)
                emitter -> categoryDao.insert(category)).subscribeOn(Schedulers.io());
    }

    public Single<Long> insertListCategory(List<Category> categoryList) {
        return Single.create((SingleOnSubscribe<Long>)
                emitter -> {
                    for (Category item : categoryList) {
//                        categoryDao.insert(item); ошибка
                     }
                }
        ).subscribeOn(Schedulers.io());
    }

    public int deleteItemCategory(Category category) {
        return categoryDao.delete(category);
    }

    public int updateItemCategory(Category category) {
        return categoryDao.update(category);
    }

    public Single<Long> insertItemGroup(Group group) {
        return Single.create((SingleOnSubscribe<Long>)
                emitter -> groupDao.insert(group)).subscribeOn(Schedulers.io());
    }

    public Single<Long> insertListGroups(List<Group> groupList) {
        return Single.create((SingleOnSubscribe<Long>)
                emitter -> {
                    for (Group item : groupList) {
                        groupDao.insert(item);
                    }
                }
        ).subscribeOn(Schedulers.io());
    }

    public int deleteItemGroup(Group group) {
        return groupDao.delete(group);
    }

    public int updateItemGroup(Group group) {
        return groupDao.delete(group);
    }

    public Single<Long> insertItemWorkForm(WorkForm workForm) {
        return Single.create((SingleOnSubscribe<Long>)
                emitter -> workFormDao.insert(workForm)).subscribeOn(Schedulers.io());
    }

    public Single<Long> insertListWorkForms(List<WorkForm> workFormList) {
        return Single.create((SingleOnSubscribe<Long>)
                emitter -> {
                    for (WorkForm item : workFormList) {
//                        workFormDao.insert(item); ошибка
                    }
                }
        ).subscribeOn(Schedulers.io());
    }

    public int deleteItemWorkForm(WorkForm workForm) {
        return workFormDao.delete(workForm);
    }

    public int updateItemWorkForm(WorkForm workForm) {
        return workFormDao.update(workForm);
    }

}
