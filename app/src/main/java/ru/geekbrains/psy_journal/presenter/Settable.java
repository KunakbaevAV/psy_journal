package ru.geekbrains.psy_journal.presenter;

import java.util.List;

import io.reactivex.Single;
import ru.geekbrains.psy_journal.model.data.OTF;
import ru.geekbrains.psy_journal.model.data.TD;
import ru.geekbrains.psy_journal.model.data.TF;

public interface Settable {
	Single<List<OTF>> getOTF();
	Single<List<TF>> getTF(int idOTF);
	Single<List<TD>> getTD(int idTF);
	void openNewFunction(String title, int id);
	void saveTD(TD td);
}
