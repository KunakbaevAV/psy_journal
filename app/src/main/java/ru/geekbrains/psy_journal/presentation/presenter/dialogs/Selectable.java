package ru.geekbrains.psy_journal.presentation.presenter.dialogs;

import ru.geekbrains.psy_journal.presentation.presenter.Derivable;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.viewholders.DisplayedSelectedFileViewHolder;

public interface Selectable extends Derivable {
	void bindView(DisplayedSelectedFileViewHolder displayed, int position);
}
