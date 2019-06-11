package ru.geekbrains.psy_journal.view.fragment;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(SingleStateStrategy.class)
public interface AllWorkView extends MvpView {
    void updateRecyclerView();

    void showProgressBar();

    void hideProgressBar();

    void showToast(String message);
}