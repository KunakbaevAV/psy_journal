package ru.geekbrains.psy_journal.presentation.presenter.view_ui.activity;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface InformedView extends MvpView {
	@StateStrategyType(SingleStateStrategy.class)
	void showStatusLoadDataBase(String error);
	@StateStrategyType(SingleStateStrategy.class)
	void showStatusClearDatabase(String error);
	@StateStrategyType(SingleStateStrategy.class)
	void showStatusWriteReport(String nameFile, String error);
	@StateStrategyType(SingleStateStrategy.class)
	void showStatusReadXml(String nameFile, String error);
}
