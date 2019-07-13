package ru.geekbrains.psy_journal.presentation.presenter.fragments;

import ru.geekbrains.psy_journal.presentation.presenter.view_ui.fragments.viewholders.IViewHolder;

public interface IRecyclerAllWorkPresenter {

    void bindView(IViewHolder iViewHolder);

    int getItemCount();

    void onClickDelete(int position);

    void onClickUpdate(IViewHolder holder);
}
