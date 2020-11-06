package ru.geekbrains.psy_journal.presentation.presenter.view_ui.fragments;

import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface ReportingView extends RecycleringView {

    @StateStrategyType(SingleStateStrategy.class)
    void showReportByTF(String codeTF, long from, long unto);

}
