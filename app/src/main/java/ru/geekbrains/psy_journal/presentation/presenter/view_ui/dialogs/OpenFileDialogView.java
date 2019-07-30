package ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(SingleStateStrategy.class)
public interface OpenFileDialogView extends MvpView {

    void updateRecyclerView();

    void showToast(String message);
}
