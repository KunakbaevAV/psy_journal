package ru.geekbrains.psy_journal.view.dialogs;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface Updated  extends MvpView {
	@StateStrategyType(SingleStateStrategy.class)
	void update();
}
