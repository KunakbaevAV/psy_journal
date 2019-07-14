package ru.geekbrains.psy_journal.presentation.presenter.view_ui.fragments;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface ReportingView extends MvpView {
    @StateStrategyType(SingleStateStrategy.class)
    void updateRecyclerView();

    @StateStrategyType(SingleStateStrategy.class)
    void showProgressBar();

    @StateStrategyType(SingleStateStrategy.class)
    void hideProgressBar();
}
