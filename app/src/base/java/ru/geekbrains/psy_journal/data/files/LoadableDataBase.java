package ru.geekbrains.psy_journal.data.files;

import java.util.List;

import ru.geekbrains.psy_journal.data.repositories.model.OTF;
import ru.geekbrains.psy_journal.data.repositories.model.TD;
import ru.geekbrains.psy_journal.data.repositories.model.TF;

public interface LoadableDataBase {

	List<OTF> getOtfList();
	List<TF> getTfList();
	List<TD> getTdList();
	void initDataBase();
}
