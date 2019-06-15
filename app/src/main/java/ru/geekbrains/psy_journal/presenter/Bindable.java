package ru.geekbrains.psy_journal.presenter;

import ru.geekbrains.psy_journal.view.dialogs.adapters.Displayed;

public interface Bindable {
	void bindView(Displayed displayed, int position);
	int getItemCount();
	void selectItem(int position);
}
