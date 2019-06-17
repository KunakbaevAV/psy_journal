package ru.geekbrains.psy_journal.presenter;

import ru.geekbrains.psy_journal.model.data.TD;

public interface Settable {
	void openNewFunction(String title, int id);
	void saveTD(TD td);
}
