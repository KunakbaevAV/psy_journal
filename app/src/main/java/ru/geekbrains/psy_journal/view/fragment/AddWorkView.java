package ru.geekbrains.psy_journal.view.fragment;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.psy_journal.model.data.TD;

public interface AddWorkView extends MvpView {

	@StateStrategyType(SingleStateStrategy.class)
	void showDate(long date);
	@StateStrategyType(SingleStateStrategy.class)
	void showHours(float hours);
	void collectAll();
	@StateStrategyType(SingleStateStrategy.class)
	void closeDialogs(TD td);
	void showToast(String message);
}
