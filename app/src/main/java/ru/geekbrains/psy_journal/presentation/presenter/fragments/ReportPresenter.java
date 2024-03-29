package ru.geekbrains.psy_journal.presentation.presenter.fragments;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ru.geekbrains.psy_journal.data.repositories.StorableTD;
import ru.geekbrains.psy_journal.data.repositories.StorableTF;
import ru.geekbrains.psy_journal.domain.models.ReportData;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.fragments.ReportingView;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.fragments.viewholders.ReportRelated;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.fragments.viewholders.ReportShown;

@InjectViewState
public class ReportPresenter extends MvpPresenter<ReportingView> {

    @Inject StorableTF storableTF;
	@Inject StorableTD storableTD;

    private Disposable disposable;

    private final RecyclePresenter recyclePresenter = new RecyclePresenter();
    private final List<ReportData> list = new ArrayList<>();
    private long from;
    private long unto;

    public RecyclePresenter getRecyclePresenter() {
        return recyclePresenter;
    }

    public void initialize(int idOTF, long from, long unto) {
        this.from = from;
        this.unto = unto;
        getViewState().showProgressBar();
        disposable = storableTF.getReport(idOTF, from, unto)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reports -> {
                            list.addAll(reports);
                            ifRequestSuccess();
                        },
                        e -> {
                            getViewState().hideProgressBar();
                            Log.e("initialize: ", e.getMessage());
                        });
    }

    private void ifRequestSuccess() {
        getViewState().updateRecyclerView();
        getViewState().hideProgressBar();
    }

    public void showReportByTF(String codeTF, long from, long unto) {
        getViewState().showProgressBar();
        disposable = storableTD.getReportByTF(codeTF, from, unto)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reports -> {
                            list.clear();
                            list.addAll(reports);
                            ifRequestSuccess();
                        },
                        e -> getViewState().hideProgressBar());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) disposable.dispose();
    }

    private class RecyclePresenter implements ReportRelated {

        @Override
        public void bindView(ReportShown reportShown, int position) {
            ReportData report = list.get(position);
            reportShown.show(String.format("%s %s", report.getCodeTF(), report.getNameTF()), report.getQuantityPeople(), report.getWorkTime());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public void selectItem(int position) {
            ReportData report = list.get(position);
            getViewState().showReportByTF(report.getCodeTF(), from, unto);
        }
    }
}
