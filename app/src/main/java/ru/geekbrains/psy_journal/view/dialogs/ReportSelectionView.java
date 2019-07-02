package ru.geekbrains.psy_journal.view.dialogs;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface ReportSelectionView extends MvpView {
	@StateStrategyType(AddToEndSingleStrategy.class)
	void showSelectedOTF(String name);
	@StateStrategyType(AddToEndSingleStrategy.class)
	void closeDialog();
	@StateStrategyType(AddToEndSingleStrategy.class)
	void showSelectedFrom(long date);
	@StateStrategyType(AddToEndSingleStrategy.class)
	void showSelectedUnto(long date);
	@StateStrategyType(AddToEndSingleStrategy.class)
	void transferData(int idOTF, long from, long unto);
}
