package ru.geekbrains.psy_journal.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import ru.geekbrains.psy_journal.model.data.Functional;
import ru.geekbrains.psy_journal.view.dialogs.OTFSelectionView;
import ru.geekbrains.psy_journal.view.fragment.Collectable;

@InjectViewState
public class OTFSelectionPresenter extends MvpPresenter<OTFSelectionView> implements
	SettableByFunction,
	SettableByDate,
	Collectable {

	private boolean isFrom;
	private int selectedOTF;
	private long from;
	private long unto;

	public void setFrom(boolean from) {
		isFrom = from;
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
