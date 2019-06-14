package ru.geekbrains.psy_journal.presenter;

import ru.geekbrains.psy_journal.view.dialogs.adapters.Displayed;

public interface Settable {

	void setDate(int year, int month, int dayOfMonth);
	void setHours( int hour, int minutes);
	void bindView(Displayed displayed, int position);
	int getItemCount();
	void toClear();
	void choose(int position);
}
