package ru.geekbrains.psy_journal.data.repositories;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.geekbrains.psy_journal.data.repositories.model.Journal;

public interface StorableJournal {

	Single<List<Journal>> getJournalList();
	Completable insertItemJournal(Journal journal);
	Completable deleteItemJournal(Journal journal);
	Completable updateItemJournal(Journal journal);
	Single<List<String>> getListFullNames();
}
