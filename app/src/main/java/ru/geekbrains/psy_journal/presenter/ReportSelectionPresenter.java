package ru.geekbrains.psy_journal.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.Calendar;

import ru.geekbrains.psy_journal.model.data.Functional;
import ru.geekbrains.psy_journal.view.dialogs.OTFSelectionView;
import ru.geekbrains.psy_journal.view.fragment.Collectable;

@InjectViewState
public class ReportSelectionPresenter extends MvpPresenter<OTFSelectionView> implements
	SettableByFunction,
	SettableByDate,
	Collectable {

    private static final int LAST_HOUR_DAY = 23;
    private static final int LAST_MINUTE_HOUR = 59;
    private static final int START = 0;
    private final Calendar calendar = Calendar.getInstance();
	private boolean isFrom;
	private int selectedOTF;
	private long from;
	private long unto;

	public void setFrom(boolean from) {
		isFrom = from;
	}

    private void setTimeInUnTo() {
        calendar.setTimeInMillis(unto);
        calendar.set(Calendar.HOUR, LAST_HOUR_DAY);
        calendar.set(Calendar.MINUTE, LAST_MINUTE_HOUR);
        unto = calendar.getTimeInMillis();
    }

    private void setTimeInFrom() {
        calendar.setTimeInMillis(from);
        calendar.set(Calendar.HOUR, START);
        calendar.set(Calendar.MINUTE, START);
        from = calendar.getTimeInMillis();
    }

	private void checkDate(){
		if (from != 0 && unto != 0){
			if (from > unto){
				long temp = from;
				from = unto;
				unto = temp;
				getViewState().showSelectedFrom(from);
				getViewState().showSelectedUnto(unto);
			}
            setTimeInFrom();
            setTimeInUnTo();
		}
	}

	@Override
	public void setFunction(Functional function, boolean close) {
		getViewState().closeDialog();
		selectedOTF = function.getId();
		getViewState().showSelectedOTF(function.getName());
	}

	@Override
	public void setDate(long date) {
		if (isFrom){
			from = date;
			getViewState().showSelectedFrom(date);
		} else {
			unto = date;
			getViewState().showSelectedUnto(date);
		}
		checkDate();
	}

	@Override
	public boolean isCollectedAll() {
		if (selectedOTF == 0 || from == 0 || unto == 0) return false;
		getViewState().transferData(selectedOTF, from, unto);
		return true;
	}
}
