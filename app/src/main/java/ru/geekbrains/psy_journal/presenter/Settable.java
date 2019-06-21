package ru.geekbrains.psy_journal.presenter;

import ru.geekbrains.psy_journal.model.data.Catalog;
import ru.geekbrains.psy_journal.model.data.Category;
import ru.geekbrains.psy_journal.model.data.Functional;
import ru.geekbrains.psy_journal.model.data.Group;
import ru.geekbrains.psy_journal.model.data.WorkForm;

public interface Settable {
	void openNewFunction(String title, int id);
	void saveSelectedFunction(Functional function);

    // Метод для сохранения выбранного элемента справочников (Категории/Группы/Формы работы)
    void saveSelectedCatalog(Catalog catalog);
	void saveSelectedCatalog(Category category);
	void saveSelectedCatalog(Group group);
	void saveSelectedCatalog(WorkForm workForm);
}
