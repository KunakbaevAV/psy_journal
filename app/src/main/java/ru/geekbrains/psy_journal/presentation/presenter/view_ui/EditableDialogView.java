package ru.geekbrains.psy_journal.presentation.presenter.view_ui;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.geekbrains.psy_journal.data.repositories.model.Catalog;
import ru.geekbrains.psy_journal.data.repositories.model.Category;
import ru.geekbrains.psy_journal.data.repositories.model.Group;
import ru.geekbrains.psy_journal.data.repositories.model.WorkForm;

@StateStrategyType(SingleStateStrategy.class)
public interface EditableDialogView extends MvpView {

    void updateRecyclerView();

    void showToast(String message);

    void saveSelectedCategory(Category catalog);

    void saveSelectedGroup(Group catalog);

    void saveSelectedWorkForm(WorkForm catalog);

    void saveSelectedCatalog(Catalog catalog);
}
