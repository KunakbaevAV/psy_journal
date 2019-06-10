package ru.geekbrains.psy_journal.model.data;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.model.data.dao.JournalDao;

public class RoomHelper {

    @Inject
    JournalDao journalDao;

    public RoomHelper() {
        App.getAppComponent().inject(this);
    }

    public Single<List<Journal>> getJournalList() {
        return journalDao.getAll().subscribeOn(Schedulers.io());
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
