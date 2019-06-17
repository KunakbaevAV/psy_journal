package ru.geekbrains.psy_journal.view.fragment;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.geekbrains.psy_journal.model.data.Functional;
import ru.geekbrains.psy_journal.model.data.TD;

public interface AddWorkView extends MvpView {

	@StateStrategyType(SingleStateStrategy.class)
	void showDate(long date);
	@StateStrategyType(SingleStateStrategy.class)
	void showHours(float hours);
	void collectAll();
	@StateStrategyType(AddToEndSingleStrategy.class)
	void openDialogue(String title, int id);
	@StateStrategyType(SingleStateStrategy.class)
	void closeDialogs(Functional function);
	void showToast(String message);
}
