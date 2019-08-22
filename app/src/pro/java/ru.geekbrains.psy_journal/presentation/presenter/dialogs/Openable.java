package ru.geekbrains.psy_journal.presentation.presenter.dialogs;

import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.viewholders.DisplayedFileViewHolder;

public interface Openable  {
	void bindView(DisplayedFileViewHolder displayed, int position);
	Selectable getSelectable();
}
