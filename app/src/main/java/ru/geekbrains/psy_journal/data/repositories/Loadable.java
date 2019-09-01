package ru.geekbrains.psy_journal.data.repositories;

import java.util.List;

import ru.geekbrains.psy_journal.data.repositories.model.Category;
import ru.geekbrains.psy_journal.data.repositories.model.Group;
import ru.geekbrains.psy_journal.data.repositories.model.OTF;
import ru.geekbrains.psy_journal.data.repositories.model.TD;
import ru.geekbrains.psy_journal.data.repositories.model.TF;
import ru.geekbrains.psy_journal.data.repositories.model.WorkForm;

public interface Loadable {

	void initializeOTF(List<OTF> list);
	void initializeTF(List<TF> list);
	void initializeTD(List<TD> list);
	void initializeCategory(List<Category> list);
	void initializeGroup(List<Group> list);
	void initializeWorkForms(List<WorkForm> list);
}
