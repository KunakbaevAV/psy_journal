package ru.geekbrains.psy_journal.model.data;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.model.data.dao.CategoryDao;
import ru.geekbrains.psy_journal.model.data.dao.GroupDao;
import ru.geekbrains.psy_journal.model.data.dao.JournalDao;
import ru.geekbrains.psy_journal.model.data.dao.OTFDao;
import ru.geekbrains.psy_journal.model.data.dao.TDDao;
import ru.geekbrains.psy_journal.model.data.dao.TFDao;
import ru.geekbrains.psy_journal.model.data.dao.WorkFormDao;

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

    public Single<List<Journal>> getJournalList() {
        return journalDao.getAll().subscribeOn(Schedulers.io());
    }

    //Возвращает список Обобщенных трудовых функций
    public Single<List<OTF>> getOTFList() {
        return otfDao.getAllOtf().subscribeOn(Schedulers.io());
    }

    //Возвращает список Трудовых функций, относящихся к указанной Обобщенной трудовой функции
    public Single<List<TF>> getTFList(int idOTF) {
        return tfDao.getTfByOtf(idOTF);
    }

    //Возвращает список Трудовых действий, относящихся к указанной Трудовой функции
    public Single<List<TD>> getTDList(int idTF) {
        return tdDao.getTdByTf(idTF);
    }

    //Возвращает список категорий людей, с которыми работает пользователь
    public Single<List<Category>> getListCategory() {
        return categoryDao.getAllCategories();
    }

    //Возвращает список групп(классов), с которыми работает пользователь
    public Single<List<Group>> getListGroups() {
        return groupDao.getAllGroups();
    }

    //Возвращает список форм работы пользователя
    public Single<List<WorkForm>> getListWorkForms() {
        return workFormDao.getAllWorkForms();
    }

    public int deleteItemJournal(Journal journal) {
        return journalDao.delete(journal);
    }

    public Long insertItemJournal(Journal journal) {
        return journalDao.insert(journal);
    }

    public int updateItemJournal(Journal journal) {
        return journalDao.update(journal);
    }

}
