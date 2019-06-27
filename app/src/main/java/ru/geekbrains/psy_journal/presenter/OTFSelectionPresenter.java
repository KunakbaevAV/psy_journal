package ru.geekbrains.psy_journal.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import java.util.Calendar;
import javax.inject.Inject;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ru.geekbrains.psy_journal.model.database.RoomHelper;
import ru.geekbrains.psy_journal.view.dialogs.Selectable;
import ru.geekbrains.psy_journal.view.fragment.Collectable;

@InjectViewState
public class OTFSelectionPresenter extends MvpPresenter<Selectable> implements Terminable, Collectable {

	@Inject	RoomHelper roomHelper;

	private boolean isFrom;
	private int selectedOTF;
	private long from;
	private long unto;

	public void setFrom(boolean from) {
		isFrom = from;
	}

	public void setSelectedOTF(int selectedOTF) {
		this.selectedOTF = selectedOTF;
	}

	public long getFrom() {
		return from;
	}

	public void getOTF(){
		Disposable disposable = roomHelper.getOTFList()
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(otfList -> getViewState().fillAdapter(otfList),
				e -> Log.e("getOTF: ", e.getMessage()));
	}

	@Override
	public void setDate(int year, int month, int dayOfMonth) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, dayOfMonth);
		long date = calendar.getTimeInMillis();
		if (isFrom){
			from = date;
			getViewState().showSelectedFrom(date);
		} else {
			unto = date;
			getViewState().showSelectedUnto(date);
		}
	}

	@Override
	public boolean isCollectedAll() {
		if (selectedOTF == 0 || from == 0 || unto == 0) return false;
		getViewState().transferData(selectedOTF, from, unto);
		return true;
	}
}
