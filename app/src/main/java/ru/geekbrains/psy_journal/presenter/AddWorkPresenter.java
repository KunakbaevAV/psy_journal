package ru.geekbrains.psy_journal.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import ru.geekbrains.psy_journal.model.data.Group;
import ru.geekbrains.psy_journal.model.data.Journal;
import ru.geekbrains.psy_journal.model.database.RoomHelper;
import ru.geekbrains.psy_journal.model.data.TD;
import ru.geekbrains.psy_journal.model.data.TF;
import ru.geekbrains.psy_journal.model.data.WorkForm;
import ru.geekbrains.psy_journal.view.fragment.AddWorkView;

@InjectViewState
public class AddWorkPresenter extends MvpPresenter<AddWorkView> implements Settable {

	private List<OTF> otfList;
	private List<TF> tfList;
	private List<TD> tdList;
	private List<Group> groupList;
	private List<WorkForm> workFormList;
	private Journal journal;

    @Inject
    RoomHelper roomHelper;

	public Journal getJournal() {
		return journal;
	}

	public AddWorkPresenter() {
		journal = new Journal();
	}



	public void addWorkIntoDatabase(){
		roomHelper.insertItemJournal(journal);
	}

    public void setGroup(Group group) {
        journal.setIdGroup(group.getId());
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
