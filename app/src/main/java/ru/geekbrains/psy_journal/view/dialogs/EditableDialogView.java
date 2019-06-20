package ru.geekbrains.psy_journal.view.dialogs;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.geekbrains.psy_journal.model.data.Catalog;

@StateStrategyType(SingleStateStrategy.class)
public interface EditableDialogView extends MvpView {

    void loadData(String title);

    void updateRecyclerView();

    void showToast(String message);

    void saveCatalogItem(Catalog catalog);
}
