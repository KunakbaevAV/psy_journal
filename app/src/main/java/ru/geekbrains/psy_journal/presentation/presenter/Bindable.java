package ru.geekbrains.psy_journal.presentation.presenter;

import ru.geekbrains.psy_journal.presentation.view.dialogs.adapters.Displayed;

public interface Bindable extends Derivable {
	void bindView(Displayed displayed, int position);
}
