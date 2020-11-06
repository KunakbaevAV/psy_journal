package ru.geekbrains.psy_journal.presentation.presenter.dialogs;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import java.util.Calendar;
import ru.geekbrains.psy_journal.data.repositories.model.Functional;
import ru.geekbrains.psy_journal.presentation.presenter.Collectable;
import ru.geekbrains.psy_journal.presentation.presenter.SettableByDate;
import ru.geekbrains.psy_journal.presentation.presenter.SettableByFunction;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.ReportSelectionView;

@InjectViewState
public class ReportSelectionPresenter extends MvpPresenter<ReportSelectionView> implements
	SettableByFunction,
	SettableByDate,
	Collectable {

    private static final int LAST_HOUR_DAY = 23;
    private static final int LAST_MINUTE_HOUR = 59;
    private static final int START_HOUR_DAY = 0;
    private static final int START_MINUTE_HOUR = 0;
    private final Calendar calendar = Calendar.getInstance();
	private boolean isFrom;
	private int selectedOTF;
	private long from;
	private long unto;

    //Fixme придумать по другому определять поля вызова выбора даты
    public void setFrom(boolean from) {
        isFrom = from;
    }

    private void setTimeInUnTo(long date) {
        calendar.setTimeInMillis(date);
        calendar.set(Calendar.HOUR, LAST_HOUR_DAY);
        calendar.set(Calendar.MINUTE, LAST_MINUTE_HOUR);
        unto = calendar.getTimeInMillis();
    }

    private void setTimeInFrom(long date) {
        calendar.setTimeInMillis(date);
        calendar.set(Calendar.HOUR, START_HOUR_DAY);
        calendar.set(Calendar.MINUTE, START_MINUTE_HOUR);
        from = calendar.getTimeInMillis();
    }

    private boolean checkDate() {
		if (from != 0 && unto != 0){
            return from > unto;
        }
        return false;
	}

	private void showFrom(long date){
		setTimeInFrom(date);
		if (checkDate()) {
			from = 0;
			getViewState().showErrorFrom(from);
		} else {
			getViewState().showSelectedFrom(date);
		}
	}

	private void showUnto(long date){
		setTimeInUnTo(date);
		if (checkDate()) {
			unto = 0;
			getViewState().showErrorUnto(unto);
		} else {
			getViewState().showSelectedUnto(date);
		}
	}

	private boolean checkFieldValues(){
    	boolean areAllFieldsFilled = true;
    	if (selectedOTF == 0){
    		areAllFieldsFilled = false;
    		getViewState().showErrorOTF(selectedOTF);
	    }
    	if (from == 0){
		    areAllFieldsFilled = false;
    		getViewState().showErrorFrom(from);
	    }
    	if (unto == 0){
		    areAllFieldsFilled = false;
    		getViewState().showErrorUnto(unto);
	    }
    	return areAllFieldsFilled;
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
            showFrom(date);
		} else {
            showUnto(date);
		}
	}

	@Override
	public boolean isCollectedAll() {
		if (checkFieldValues()) {
			getViewState().transferData(selectedOTF, from, unto);
			return true;
		}
		return false;
	}
}
