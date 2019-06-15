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
import ru.geekbrains.psy_journal.model.data.TD;
import ru.geekbrains.psy_journal.model.database.RoomHelper;
import ru.geekbrains.psy_journal.model.data.WorkForm;
import ru.geekbrains.psy_journal.view.dialogs.Updated;
import ru.geekbrains.psy_journal.view.dialogs.adapters.Displayed;
import ru.geekbrains.psy_journal.view.fragment.AddWorkView;

@InjectViewState
public class AddWorkPresenter extends MvpPresenter<AddWorkView> implements
	Settable,
	Terminable{

	private static final float HOUR_IN_MINUTES = 60.0f;
	private final RecyclePresenter recyclePresenter = new RecyclePresenter();
	private final List<Functional> currentList = new ArrayList<>();
	private final Journal journal;
	private Updated updated;
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

	private void getOTF(){
		Disposable disposable = roomHelper.getOTFList()
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(list ->{
					if (!currentList.isEmpty()) currentList.clear();
					currentList.addAll(list);
					updated.update("OTF");
				},
				e -> Log.e("getOTF(): ", e.getMessage()));
	}

	private void getTF(int idOTF){
		Disposable disposable = roomHelper.getTFList(idOTF)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(list -> {
					if (!currentList.isEmpty()) currentList.clear();
					currentList.addAll(list);
					updated.update("TF");
				},
				e -> Log.e("getTF(): ", e.getMessage()));
	}

	private void getTD(int idTF){
		Disposable disposable = roomHelper.getTDList(idTF)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(list -> {
					if (!currentList.isEmpty()) currentList.clear();
					currentList.addAll(list);
					updated.update("TD");
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
		float hours = hour + minutes / HOUR_IN_MINUTES;
		journal.setWorkTime(hours);
		getViewState().showHours(hours);
	}

	@Override
	public Bindable setBind() {
		return recyclePresenter;
	}

	@Override
	public void setUpdated(Updated updated) {
		this.updated = updated;
		getOTF();
	}

	private class RecyclePresenter implements Bindable{

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
		public void selectItem(int position) {
			Functional function = currentList.get(position);
			switch (function.getLabel()){
				case "OTF":
					getTF(function.getId());
					break;
				case "TF":
					getTD(function.getId());
					break;
				case "TD":
					getViewState().closeDialogs((TD) function);
					currentList.clear();
					break;
			}
		}
	}
}
