package ru.geekbrains.psy_journal.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.psy_journal.model.data.Journal;
import ru.geekbrains.psy_journal.model.data.RoomHelper;
import ru.geekbrains.psy_journal.view.fragment.AllWorkView;
import ru.geekbrains.psy_journal.view.fragment.IViewHolder;

@InjectViewState
public class AllWorkPresenter extends MvpPresenter<AllWorkView> {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());
    @Inject
    RoomHelper roomHelper;
    private RecyclerAllWorkPresenter recyclerAllWorkPresenter;
    private List<Journal> listWorks;

    public AllWorkPresenter() {
        recyclerAllWorkPresenter = new RecyclerAllWorkPresenter();
        listWorks = new ArrayList<>();
    }

    @Override
    public void onFirstViewAttach() {
        showAllWorkRecycler();
    }

    @SuppressLint("CheckResult")
    private void showAllWorkRecycler() {
        getViewState().showProgressBar();
        roomHelper.getJournalList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(journalList -> {
                    listWorks.addAll(journalList);
                    ifRequestSuccess();
                }, throwable -> {
                    getViewState().showToast("Error loading data from database" + throwable.getMessage());
                    getViewState().hideProgressBar();
                });
    }

    private void ifRequestSuccess() {
        getViewState().updateRecyclerView();
        getViewState().hideProgressBar();
    }

    public RecyclerAllWorkPresenter getRecyclerAllWorkPresenter() {
        return recyclerAllWorkPresenter;
    }

    public class RecyclerAllWorkPresenter implements IRecyclerAllWorkPresenter {

        @Override
        public void bindView(IViewHolder holder) {
            Journal journalItem = listWorks.get(holder.getPos());
            holder.setWork(getDate(journalItem.getDate()), journalItem.getDeclaredRequest());
        }

        @Override
        public int getItemCount() {
            if (listWorks != null) {
                return listWorks.size();
            }
            return 0;
        }

        private String getDate(long date) {
            return dateFormat.format(new Date(date));
        }
    }
}
