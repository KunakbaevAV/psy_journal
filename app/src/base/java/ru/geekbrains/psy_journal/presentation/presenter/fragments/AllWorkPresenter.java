package ru.geekbrains.psy_journal.presentation.presenter.fragments;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.data.repositories.StorableJournal;
import ru.geekbrains.psy_journal.data.repositories.model.Journal;
import ru.geekbrains.psy_journal.presentation.presenter.IRecyclerAllWorkPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.fragments.AllWorkView;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.fragments.viewholders.IViewHolder;

import static ru.geekbrains.psy_journal.Constants.ERROR_DELETING;
import static ru.geekbrains.psy_journal.Constants.ERROR_LOADING_DATA_FROM_DATABASE;

@InjectViewState
public class AllWorkPresenter extends MvpPresenter<AllWorkView> {

    @Inject StorableJournal storableJournal;
	private final SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.PATTERN_DATE, Locale.getDefault());
    private final RecyclerAllWorkPresenter recyclerAllWorkPresenter;
    private final List<Journal> listWorks;
    private Disposable disposable;

	public RecyclerAllWorkPresenter getRecyclerAllWorkPresenter() {
		return recyclerAllWorkPresenter;
	}

    public AllWorkPresenter() {
        recyclerAllWorkPresenter = new RecyclerAllWorkPresenter();
        listWorks = new ArrayList<>();
    }

    @Override
    public void onFirstViewAttach() {
        showAllWorkRecycler();
    }

    private void showAllWorkRecycler() {
        getViewState().showProgressBar();
        disposable = storableJournal.getJournalList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(journalList -> {
                    listWorks.addAll(journalList);
                    Collections.sort(listWorks);
                    ifRequestSuccess();
                }, throwable -> {
                    getViewState().showToast(ERROR_LOADING_DATA_FROM_DATABASE + throwable.getMessage());
                    getViewState().hideProgressBar();
                });
    }

    private void deleteItemJournalFromDatabase(int position) {
	    getViewState().showProgressBar();
        disposable = storableJournal.deleteItemJournal(listWorks.get(position))
	        .observeOn(AndroidSchedulers.mainThread())
            .subscribe(() -> {
		            listWorks.remove(position);
		            getViewState().deleteItemRecycler(position);
		            getViewState().hideProgressBar();
	            },
                    er -> {
            	        getViewState().showToast(ERROR_DELETING + er);
	                    getViewState().hideProgressBar();
                    }
                );
    }

    private void ifRequestSuccess() {
        getViewState().updateRecyclerView();
        getViewState().hideProgressBar();
    }

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (disposable != null) disposable.dispose();
	}

	private class RecyclerAllWorkPresenter implements IRecyclerAllWorkPresenter {

        @Override
        public void bindView(IViewHolder holder, int position) {
            Journal journalItem = listWorks.get(position);
            holder.setWork(getDate(journalItem.getDate()), journalItem.getDeclaredRequest());
        }

	    private String getDate(long date) {
            return dateFormat.format(new Date(date));
        }

	    @Override
	    public void delete(int position) {
		    deleteItemJournalFromDatabase(position);
	    }

        @Override
        public int getItemCount() {
            return (listWorks != null) ? listWorks.size() : 0;
        }

	    @Override
	    public void selectItem(int position) {
		    getViewState().openScreenUpdateJournal(listWorks.get(position));
	    }
    }
}
