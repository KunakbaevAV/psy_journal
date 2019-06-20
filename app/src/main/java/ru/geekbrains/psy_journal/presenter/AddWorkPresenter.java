package ru.geekbrains.psy_journal.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.psy_journal.model.data.Catalog;
import ru.geekbrains.psy_journal.model.data.Functional;
import ru.geekbrains.psy_journal.model.data.Group;
import ru.geekbrains.psy_journal.model.data.Journal;
import ru.geekbrains.psy_journal.model.data.WorkForm;
import ru.geekbrains.psy_journal.model.database.RoomHelper;
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

	public AddWorkPresenter(Journal journal) {
		if (journal == null) this.journal = new Journal();
		else {
			this.journal = journal;
			init();
		}
	}

	private void init(){
		getViewState().showDate(journal.getDate());
		getViewState().showNumberOfPeople(journal.getQuantityPeople());
		getViewState().showHours(journal.getWorkTime());
//		getViewState().showCategory();
//		getViewState().showGroup();
		getViewState().showName(journal.getName());
		getViewState().showDeclaredRequest(journal.getDeclaredRequest());
		getViewState().showRealRequest(journal.getRealRequest());
//		getViewState().showWorkForm();
//		getViewState().showTd();
		getViewState().showComment(journal.getComment());
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
	public void saveSelectedFunction(Functional function) {
		getViewState().closeDialogs();
		getViewState().showTd(function);
	}

    @Override
    public void saveSelectedCatalog(Catalog catalog) {
        //TODO Метод для сохранения выбранного элемента справочников (Категории/Группы/Формы работы)
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
