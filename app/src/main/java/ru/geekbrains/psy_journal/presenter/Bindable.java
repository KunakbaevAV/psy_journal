package ru.geekbrains.psy_journal.presenter;

import ru.geekbrains.psy_journal.view.dialogs.adapters.Displayed;

public interface Bindable extends Derivable {
	void bindView(Displayed displayed, int position);
}
