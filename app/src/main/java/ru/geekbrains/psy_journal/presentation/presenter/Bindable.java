package ru.geekbrains.psy_journal.presentation.presenter;

import ru.geekbrains.psy_journal.presentation.presenter.view_ui.Displayed;

public interface Bindable extends Derivable {
	void bindView(Displayed displayed, int position);
}
