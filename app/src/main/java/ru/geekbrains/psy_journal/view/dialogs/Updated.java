package ru.geekbrains.psy_journal.view.dialogs;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.geekbrains.psy_journal.model.data.Functional;

public interface Updated  extends MvpView {

	@StateStrategyType(SingleStateStrategy.class)
	void update();
    @StateStrategyType(SkipStrategy.class)
    void openNewFeature(Functional functional);
}
