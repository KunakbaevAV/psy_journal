package ru.geekbrains.psy_journal.data.repositories;

import java.util.List;

import io.reactivex.Single;
import ru.geekbrains.psy_journal.domain.models.ReportingJournal;

public interface StorableReportingJournals {
	Single<List<ReportingJournal>> getListReportingJournals();
}
