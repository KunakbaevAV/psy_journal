package ru.geekbrains.psy_journal.presentation.presenter;

import ru.geekbrains.psy_journal.presentation.presenter.view_ui.IViewHolder;

public interface IRecyclerAllWorkPresenter {

    void bindView(IViewHolder iViewHolder);

    int getItemCount();

    void onClickDelete(int position);

    void onClickUpdate(IViewHolder holder);
}
