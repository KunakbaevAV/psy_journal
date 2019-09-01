package ru.geekbrains.psy_journal.data.repositories;

import java.util.List;

import io.reactivex.Single;
import ru.geekbrains.psy_journal.data.repositories.model.TD;
import ru.geekbrains.psy_journal.domain.models.ReportData;

public interface StorableTD {

	Single<List<TD>> getTDList(int idTF);
	Single<List<ReportData>> getReportByTF(String codeTF, long dateFrom, long dateTo);
}
