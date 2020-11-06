package ru.geekbrains.psy_journal.presentation.presenter;

import ru.geekbrains.psy_journal.data.repositories.model.Functional;

public interface SettableByFunction {
	void setFunction(Functional function, boolean close);
}
