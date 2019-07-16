package ru.geekbrains.psy_journal.domain.file;

import java.io.File;
import java.util.List;

import io.reactivex.Single;
import ru.geekbrains.psy_journal.domain.models.ReportingJournal;

public interface CreatedByExcel {
	Single<File> create(List<ReportingJournal> journals);
}
