package ru.geekbrains.psy_journal.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.Calendar;

import ru.geekbrains.psy_journal.model.data.Journal;
import ru.geekbrains.psy_journal.view.fragment.Displayed;
@InjectViewState
public class AddWorkPresenter extends MvpPresenter<Displayed> implements Settable {

	private Journal journal;

	public AddWorkPresenter(Journal journal) {
		this.journal = journal;
	}

	@Override
	public void setDate(int year, int month, int dayOfMonth) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, dayOfMonth);
		long date = calendar.getTimeInMillis();
		journal.setDate(date);
		getViewState().showDate(date);
	}
}
