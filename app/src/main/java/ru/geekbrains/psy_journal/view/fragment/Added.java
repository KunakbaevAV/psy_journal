package ru.geekbrains.psy_journal.view.fragment;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface Added extends MvpView {

	@StateStrategyType(SingleStateStrategy.class)
	void showDate(long date);
	@StateStrategyType(SingleStateStrategy.class)
	void showHours(float hours);
	void collectAll();

	void showToast(String message);
}
