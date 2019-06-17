package ru.geekbrains.psy_journal.view.dialogs;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.geekbrains.psy_journal.model.data.Functional;

public interface Updated  extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void loadData(String title, int id);
	@StateStrategyType(SingleStateStrategy.class)
	void update();

    @StateStrategyType(SingleStateStrategy.class)
    void openNewFeature(Functional functional);
}
