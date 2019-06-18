package ru.geekbrains.psy_journal.presenter;

import ru.geekbrains.psy_journal.model.data.Catalog;
import ru.geekbrains.psy_journal.model.data.Functional;

public interface Settable {
	void openNewFunction(String title, int id);
	void saveSelectedFunction(Functional function);

    // Метод для сохранения выбранного элемента справочников (Категории/Группы/Формы работы)
    void saveSelectedCatalog(Catalog catalog);
}
