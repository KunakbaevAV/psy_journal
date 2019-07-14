package ru.geekbrains.psy_journal.presentation.presenter.dialogs;

import ru.geekbrains.psy_journal.presentation.presenter.Derivable;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.viewholders.Displayed;

public interface Bindable extends Derivable {
	void bindView(Displayed displayed, int position);
}
