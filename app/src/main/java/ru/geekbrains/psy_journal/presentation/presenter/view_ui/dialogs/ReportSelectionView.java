package ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs;

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
	void showErrorFrom();
	@StateStrategyType(AddToEndSingleStrategy.class)
	void showErrorUnto();
	@StateStrategyType(AddToEndSingleStrategy.class)
	void transferData(int idOTF, long from, long unto);
}
