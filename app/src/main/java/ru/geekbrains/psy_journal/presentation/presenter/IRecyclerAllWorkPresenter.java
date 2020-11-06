package ru.geekbrains.psy_journal.presentation.presenter;

import ru.geekbrains.psy_journal.presentation.presenter.view_ui.fragments.viewholders.IViewHolder;

public interface IRecyclerAllWorkPresenter  extends Derivable {

    void bindView(IViewHolder iViewHolder, int position);
    void delete(int position);
}
