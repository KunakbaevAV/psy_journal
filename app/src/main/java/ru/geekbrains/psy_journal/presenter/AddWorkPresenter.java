package ru.geekbrains.psy_journal.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import java.util.Calendar;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.psy_journal.model.data.Group;
import ru.geekbrains.psy_journal.model.data.Journal;
import ru.geekbrains.psy_journal.model.data.TD;
import ru.geekbrains.psy_journal.model.database.RoomHelper;
import ru.geekbrains.psy_journal.model.data.WorkForm;
import ru.geekbrains.psy_journal.view.fragment.AddWorkView;

@InjectViewState
public class AddWorkPresenter extends MvpPresenter<AddWorkView> implements
	Settable,
	Terminable{

	private static final float HOUR_IN_MINUTES = 60.0f;
	private final Journal journal;

	private List<Group> groupList;
	private List<WorkForm> workFormList;

    @Inject
    RoomHelper roomHelper;

	public Journal getJournal() {
		return journal;
	}

	public AddWorkPresenter() {
		journal = new Journal();
	}

	public void addWorkIntoDatabase(){
		roomHelper.insertItemJournal(journal)
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe();
	}

    public void setGroup(Group group) {
        journal.setIdGroup(group.getId());
	}

	@Override
	public void openNewFunction(String title, int id) {
		getViewState().openDialogue(title, id);
	}

	@Override
	public void saveTD(TD td) {
		getViewState().closeDialogs(td);
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
		float hours = hour + minutes / HOUR_IN_MINUTES;
		journal.setWorkTime(hours);
		getViewState().showHours(hours);
	}
}
