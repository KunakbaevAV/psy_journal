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

    public Long insertItemJournal(Journal journal) {
        return journalDao.insert(journal);
    }

    public int deleteItemJournal(Journal journal) {
        return journalDao.delete(journal);
    }

    public int updateItemJournal(Journal journal) {
        return journalDao.update(journal);
    }

    public Long insertItemOTF(OTF otf) {
        return otfDao.insert(otf);
    }

    public int deleteItemOTF(OTF otf) {
        return otfDao.delete(otf);
    }

    public int updateItemOTF(OTF otf) {
        return otfDao.update(otf);
    }

    public Long insertItemTF(TF tf) {
        return tfDao.insert(tf);
    }

    public int deleteItemTF(TF tf) {
        return tfDao.delete(tf);
    }

    public int updateItemTF(TF tf) {
        return tfDao.update(tf);
    }

    public Long insertItemTD(TD td) {
        return tdDao.insert(td);
    }

    public int deleteItemTD(TD td) {
        return tdDao.delete(td);
    }

    public int updateItemTD(TD td) {
        return tdDao.update(td);
    }

    public Long insertItemCategory(Category category) {
        return categoryDao.insert(category);
    }

    public int deleteItemCategory(Category category) {
        return categoryDao.delete(category);
    }

    public int updateItemCategory(Category category) {
        return categoryDao.update(category);
    }

    public Long insertItemGroup(Group group) {
        return groupDao.insert(group);
    }

    public int deleteItemGroup(Group group) {
        return groupDao.delete(group);
    }

    public int updateItemGroup(Group group) {
        return groupDao.delete(group);
    }

    public Long insertItemWorkForm(WorkForm workForm) {
        return workFormDao.insert(workForm);
    }

    public int deleteItemWorkForm(WorkForm workForm) {
        return workFormDao.delete(workForm);
    }

    public int updateItemWorkForm(WorkForm workForm) {
        return workFormDao.update(workForm);
    }

}
