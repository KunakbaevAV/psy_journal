package ru.geekbrains.psy_journal.presenter;

import ru.geekbrains.psy_journal.model.data.Functional;

public interface SettableByFunction {
	void setFunction(Functional function, boolean close);
}
