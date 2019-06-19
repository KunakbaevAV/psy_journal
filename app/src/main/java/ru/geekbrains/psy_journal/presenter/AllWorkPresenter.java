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

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.psy_journal.model.data.Journal;
import ru.geekbrains.psy_journal.model.database.RoomHelper;
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

    @SuppressLint("CheckResult")
    private void deleteItemJournalFromDatabase(Journal journal) {
        deleteItemJournalFromDatabaseObservable(journal).observeOn(AndroidSchedulers.mainThread())
                .subscribe(hits -> {
                            getViewState().updateRecyclerView();
                            getViewState().showToast("Item Journal deleted from database");
                        },
                        throwable -> getViewState().showToast("Error deleting: " + throwable));
    }

    private Single<Integer> deleteItemJournalFromDatabaseObservable(Journal journal) {
        return roomHelper.deleteItemJournal(journal);
    }

    private void openScreenUpdateJournal(Journal journal) {
        // TODO Метод открытия окна для редактирования единицы работы
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

        @Override
        public void onClickDelete(int positiom) {
            deleteItemJournalFromDatabase(listWorks.get(positiom));
            listWorks.remove(positiom);
        }

        @Override
        public void onClickUpdate(IViewHolder holder) {
            openScreenUpdateJournal(listWorks.get(holder.getPos()));
        }
    }

}
