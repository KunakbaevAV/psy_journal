package ru.geekbrains.psy_journal.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.Calendar;

import ru.geekbrains.psy_journal.model.data.Group;
import ru.geekbrains.psy_journal.model.data.Journal;
import ru.geekbrains.psy_journal.view.fragment.Added;
@InjectViewState
public class AddWorkPresenter extends MvpPresenter<Added> implements Settable {

	private Journal journal;
	//инжектировать класс для отправки Journal в БД

	public Journal getJournal() {
		return journal;
	}

	public AddWorkPresenter(Journal journal) {
		this.journal = journal;
	}

	public void addWorkIntoDatabase(){
		//здесь передать в базу journal
	}

	public void setNameGroup(String name){
		journal.setGroup(new Group(name));
	}

	@Override
	public void setDate(int year, int month, int dayOfMonth) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, dayOfMonth);
		long date = calendar.getTimeInMillis();
		journal.setDate(date);
		getViewState().showDate(date);
	}

	@Override
	public void setHours(int hour, int minutes) {
		float hours = hour + minutes / 60.0f;
		journal.setWorkTime(hours);
		getViewState().showHours(hours);
	}
}
