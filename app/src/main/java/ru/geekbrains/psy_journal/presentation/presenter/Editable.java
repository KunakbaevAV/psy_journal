package ru.geekbrains.psy_journal.presentation.presenter;

import ru.geekbrains.psy_journal.presentation.presenter.dialogs.Bindable;

public interface Editable extends Bindable {
	void selectItem(String name, int position);
	void delete(int position);
}
