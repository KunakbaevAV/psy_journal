package ru.geekbrains.psy_journal.presentation.view.fragment;

import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.geekbrains.psy_journal.data.repositories.model.Journal;

@StateStrategyType(SingleStateStrategy.class)
public interface AllWorkView extends ReportingView {

    void showToast(String message);

    void openScreenUpdateJournal(Journal journal);
}
