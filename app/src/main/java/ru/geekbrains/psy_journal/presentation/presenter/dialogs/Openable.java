package ru.geekbrains.psy_journal.presentation.presenter.dialogs;

import ru.geekbrains.psy_journal.presentation.presenter.Derivable;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.viewholders.DisplayedFileViewHolder;

public interface Openable extends Derivable {
	void bindView(DisplayedFileViewHolder displayed, int position);
}
