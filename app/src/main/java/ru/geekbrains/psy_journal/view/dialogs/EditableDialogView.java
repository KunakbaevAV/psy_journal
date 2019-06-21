package ru.geekbrains.psy_journal.view.dialogs;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.geekbrains.psy_journal.model.data.Category;
import ru.geekbrains.psy_journal.model.data.Group;
import ru.geekbrains.psy_journal.model.data.WorkForm;

@StateStrategyType(SingleStateStrategy.class)
public interface EditableDialogView extends MvpView {

    void loadData(String title);

    void updateRecyclerView();

    void showToast(String message);

    void saveSelectedCategory(Category catalog);

    void saveSelectedGroup(Group catalog);

    void saveSelectedWorkForm(WorkForm catalog);
}
