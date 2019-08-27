package ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs;

import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.geekbrains.psy_journal.presentation.presenter.view_ui.fragments.RecycleringView;

@StateStrategyType(SingleStateStrategy.class)
public interface EditableView extends RecycleringView {

    void performAction(String nameCatalog);
}
