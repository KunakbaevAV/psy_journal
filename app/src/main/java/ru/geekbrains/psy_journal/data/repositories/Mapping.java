package ru.geekbrains.psy_journal.data.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.psy_journal.data.database.dao.CategoryDao;
import ru.geekbrains.psy_journal.data.database.dao.GroupDao;
import ru.geekbrains.psy_journal.data.database.dao.JournalDao;
import ru.geekbrains.psy_journal.data.database.dao.WorkFormDao;
import ru.geekbrains.psy_journal.data.repositories.model.Category;
import ru.geekbrains.psy_journal.data.repositories.model.Group;
import ru.geekbrains.psy_journal.data.repositories.model.Journal;
import ru.geekbrains.psy_journal.data.repositories.model.WorkForm;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.domain.models.ReportingJournal;

public class Mapping implements StorableReportingJournals{

    @Inject CategoryDao categoryDao;
    @Inject GroupDao groupDao;
    @Inject WorkFormDao workFormDao;
    @Inject JournalDao journalDao;

    public Mapping() {
        App.getAppComponent().inject(this);
    }

	/**
	 * Метод получения из БД списка {@link ReportingJournal}
	 * с подставлением нужных значений из сущностей:
	 * {@link Journal}
	 * {@link Category}
	 * {@link Group}
	 * {@link WorkForm}
	 *
	 * @return список {@link ReportingJournal}
	 */
	@Override
	public Single<List<ReportingJournal>> getListReportingJournals() {
		return Single.fromCallable(this::getListReportingJournal)
			.subscribeOn(Schedulers.io());
	}

    private List<ReportingJournal> getListReportingJournal() {
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
        reportingJournal.setDayOfWeek(journal.getDate());
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
