package ru.geekbrains.psy_journal.view.fragment;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface AddWorkView extends MvpView {

	@StateStrategyType(SingleStateStrategy.class)
	void showDate(long date);
	@StateStrategyType(SingleStateStrategy.class)
	void showHours(float hours);
	void collectAll();
	@StateStrategyType(SingleStateStrategy.class)
	void openDialogue(String title);
	@StateStrategyType(SingleStateStrategy.class)
	void closeDialogs(String code);
	void showToast(String message);
}
