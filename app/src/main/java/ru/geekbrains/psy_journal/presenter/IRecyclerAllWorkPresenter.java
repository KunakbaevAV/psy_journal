package ru.geekbrains.psy_journal.presenter;

import ru.geekbrains.psy_journal.view.fragment.IViewHolder;

public interface IRecyclerAllWorkPresenter {

    void bindView(IViewHolder iViewHolder);

    int getItemCount();

    void onClickDelete(int position);

    void onClickUpdate(IViewHolder holder);
}
