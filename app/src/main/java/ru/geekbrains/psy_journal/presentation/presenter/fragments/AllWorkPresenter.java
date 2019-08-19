package ru.geekbrains.psy_journal.presentation.presenter.fragments;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.data.repositories.RoomHelper;
import ru.geekbrains.psy_journal.data.repositories.model.Journal;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.fragments.AllWorkView;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.fragments.viewholders.IViewHolder;

import static ru.geekbrains.psy_journal.Constants.ERROR_DELETING;
import static ru.geekbrains.psy_journal.Constants.ERROR_LOADING_DATA_FROM_DATABASE;

@InjectViewState
public class AllWorkPresenter extends MvpPresenter<AllWorkView> implements Updated {

    @Inject RoomHelper roomHelper;
	private final SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.PATTERN_DATE, Locale.getDefault());
    private final RecyclerAllWorkPresenter recyclerAllWorkPresenter;
    private final List<Journal> listWorks;

	public RecyclerAllWorkPresenter getRecyclerAllWorkPresenter() {
		return recyclerAllWorkPresenter;
	}

    public AllWorkPresenter() {
        recyclerAllWorkPresenter = new RecyclerAllWorkPresenter();
        listWorks = new ArrayList<>();
    }

    //метод обновления фрагмента при обновлении профстандарта в базе.
	//можно использовать и в других обновлениях
	@Override
    public void update(){
    	if (!listWorks.isEmpty()){
    		listWorks.clear();
	    }
    	showAllWorkRecycler();
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
                    Collections.sort(listWorks);
                    ifRequestSuccess();
                }, throwable -> {
                    getViewState().showToast(ERROR_LOADING_DATA_FROM_DATABASE + throwable.getMessage());
                    getViewState().hideProgressBar();
                }).isDisposed();
    }

    @SuppressLint("CheckResult")
    private void deleteItemJournalFromDatabase(Journal journal) {
        deleteItemJournalFromDatabaseObservable(journal).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> getViewState().updateRecyclerView(),
                        er -> getViewState().showToast(ERROR_DELETING + er)
                ).isDisposed();
    }

    private Completable deleteItemJournalFromDatabaseObservable(Journal journal) {
        return roomHelper.deleteItemJournal(journal);
    }

    private void openScreenUpdateJournal(Journal journal) {
        getViewState().openScreenUpdateJournal(journal);
    }

    private void ifRequestSuccess() {
        getViewState().updateRecyclerView();
        getViewState().hideProgressBar();
    }

    private class RecyclerAllWorkPresenter implements IRecyclerAllWorkPresenter {

        @Override
        public void bindView(IViewHolder holder) {
            Journal journalItem = listWorks.get(holder.getPos());
            holder.setWork(getDate(journalItem.getDate()), journalItem.getDeclaredRequest());
        }

        private String getDate(long date) {
            return dateFormat.format(new Date(date));
        }

        @Override
        public int getItemCount() {
            if (listWorks != null) {
                return listWorks.size();
            }
            return 0;
        }

        @Override
        public void onClickDelete(int position) {
            deleteItemJournalFromDatabase(listWorks.get(position));
            listWorks.remove(position);
        }

        @Override
        public void onClickUpdate(IViewHolder holder) {
            openScreenUpdateJournal(listWorks.get(holder.getPos()));
        }
    }
}
