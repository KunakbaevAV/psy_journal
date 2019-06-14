package ru.geekbrains.psy_journal.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ru.geekbrains.psy_journal.model.data.Functional;
import ru.geekbrains.psy_journal.model.data.Group;
import ru.geekbrains.psy_journal.model.data.Journal;
import ru.geekbrains.psy_journal.model.data.OTF;
import ru.geekbrains.psy_journal.model.data.TD;
import ru.geekbrains.psy_journal.model.data.TF;
import ru.geekbrains.psy_journal.model.database.RoomHelper;
import ru.geekbrains.psy_journal.model.data.WorkForm;
import ru.geekbrains.psy_journal.view.dialogs.adapters.Displayed;
import ru.geekbrains.psy_journal.view.fragment.AddWorkView;

@InjectViewState
public class AddWorkPresenter extends MvpPresenter<AddWorkView> implements Settable {

	private final List<Functional> currentList = new ArrayList<>();
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

	public void getOTF(){
		Disposable disposable = roomHelper.getOTFList()
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(otfs -> {
					if (!currentList.isEmpty()) currentList.clear();
					currentList.addAll(otfs);
				},
				e -> Log.e("getOTF(): ", e.getMessage()));
	}

	private void getTF(int idOTF){
		Disposable disposable = roomHelper.getTFList(idOTF)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(list -> {
					if (!currentList.isEmpty()) currentList.clear();
					currentList.addAll(list);
				},
				e -> Log.e("getTF(): ", e.getMessage()));
	}

	private void getTD(int idTF){
		Disposable disposable = roomHelper.getTDList(idTF)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(list -> {
					if (!currentList.isEmpty()) currentList.clear();
					currentList.addAll(list);
				},
				e -> Log.e("getTD: ", e.getMessage()));
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

	@Override
	public void bindView(Displayed displayed, int position) {
		Functional function = currentList.get(position);
		displayed.bind(function.getCode(), function.getName());
	}

	@Override
	public int getItemCount() {
		return currentList.size();
	}

	@Override
	public void toClear() {
		currentList.clear();
	}

	@Override
	public void choose(int position) {
		Functional function = currentList.get(position);
		if (function instanceof OTF){
			getTF(((OTF) function).getId());
			getViewState().openDialogue("TF", "Tag OTF");
		}
		if (function instanceof TF){
			getTD(((TF) function).getId());
			getViewState().openDialogue("TD", "Tag TF");
		}
		if (function instanceof TD){
			getViewState().closeDialogs(function.getCode());
			currentList.clear();
		}
	}
}
