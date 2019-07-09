package ru.geekbrains.psy_journal.domain.file;

import java.io.IOException;
import java.util.List;

import ru.geekbrains.psy_journal.domain.models.ReportingJournal;

public interface CreatedByExcel {
	void create(List<ReportingJournal> journals) throws IOException;
}
