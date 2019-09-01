package ru.geekbrains.psy_journal.data.repositories;

import java.util.List;

import io.reactivex.Single;
import ru.geekbrains.psy_journal.data.repositories.model.TF;
import ru.geekbrains.psy_journal.domain.models.ReportData;

public interface StorableTF {

	Single<List<TF>> getTFList(int idOTF);
	Single<List<ReportData>> getReport(int idOTF, long dateFrom, long dateTo);
}
