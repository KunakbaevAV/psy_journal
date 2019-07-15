package ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import ru.geekbrains.psy_journal.data.repositories.model.Functional;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.fragments.ReportingView;

public interface FunctionView extends ReportingView {

    @StateStrategyType(SkipStrategy.class)
    void openNewFeature(Functional functional);
}
