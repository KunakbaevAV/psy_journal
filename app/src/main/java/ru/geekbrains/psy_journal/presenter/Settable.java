package ru.geekbrains.psy_journal.presenter;

import ru.geekbrains.psy_journal.model.data.Category;
import ru.geekbrains.psy_journal.model.data.Functional;
import ru.geekbrains.psy_journal.model.data.Group;
import ru.geekbrains.psy_journal.model.data.WorkForm;
import ru.geekbrains.psy_journal.view.dialogs.FunctionDialog;

public interface Settable {
	void openNewFunction(FunctionDialog dialog, String title);
	void saveSelectedFunction(Functional function);

	// Методы для сохранения выбранного элемента справочников (Категории/Группы/Формы работы)
	void saveSelectedCategory(Category category);

	void saveSelectedGroup(Group group);

	void saveSelectedWorkForm(WorkForm workForm);
}
