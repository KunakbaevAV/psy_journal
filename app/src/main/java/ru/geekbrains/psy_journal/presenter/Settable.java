package ru.geekbrains.psy_journal.presenter;

import ru.geekbrains.psy_journal.model.data.Functional;

public interface Settable {
	void openNewFunction(String title, int id);

    void saveSelectedFunction(Functional function);
}
