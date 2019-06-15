package ru.geekbrains.psy_journal.presenter;

import ru.geekbrains.psy_journal.view.dialogs.Updated;

public interface Settable {
	Bindable setBind();
	void setUpdated(Updated updated);
}
