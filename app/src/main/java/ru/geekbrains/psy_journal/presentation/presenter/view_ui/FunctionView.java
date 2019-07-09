package ru.geekbrains.psy_journal.presentation.presenter.view_ui;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.geekbrains.psy_journal.data.repositories.model.Functional;

public interface FunctionView extends MvpView {
	@StateStrategyType(SingleStateStrategy.class)
	void update();
    @StateStrategyType(SkipStrategy.class)
    void openNewFeature(Functional functional);
}
