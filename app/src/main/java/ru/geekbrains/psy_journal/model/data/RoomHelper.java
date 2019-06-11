package ru.geekbrains.psy_journal.model.data;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.model.data.dao.JournalDao;
import ru.geekbrains.psy_journal.model.data.dao.OTFDao;
import ru.geekbrains.psy_journal.model.data.dao.TDDao;
import ru.geekbrains.psy_journal.model.data.dao.TFDao;

public class RoomHelper {

    @Inject
    JournalDao journalDao;

    @Inject
    OTFDao otfDao;

    @Inject
    TFDao tfDao;

    @Inject
    TDDao tdDao;

    public RoomHelper() {
        App.getAppComponent().inject(this);
    }

    public Single<List<Journal>> getJournalList() {
        return journalDao.getAll().subscribeOn(Schedulers.io());
    }

    public Single<List<OTF>> getOTFList() {
        return otfDao.getAllOtf().subscribeOn(Schedulers.io());
    }

    public Single<List<TF>> getTFList(int idOTF) {
        return tfDao.getTfByOtf(idOTF);
    }

    public Single<List<TD>> getTDList(int idTF) {
        return tdDao.getTdByTf(idTF);
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
