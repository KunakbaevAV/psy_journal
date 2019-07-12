package ru.geekbrains.psy_journal.presentation.presenter;

import android.util.Log;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ru.geekbrains.psy_journal.domain.models.ReportData;
import ru.geekbrains.psy_journal.data.repositories.RoomHelper;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.ReportingView;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.ReportRelated;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.ReportShown;

@InjectViewState
public class ReportPresenter extends MvpPresenter<ReportingView> {

    private final RecyclePresenter recyclePresenter = new RecyclePresenter();
    private final List<ReportData> list = new ArrayList<>();
    @Inject
    RoomHelper roomHelper;
    private Disposable disposable;

    public RecyclePresenter getRecyclePresenter() {
        return recyclePresenter;
    }

    public void initialize(int idOTF, long from, long unto) {
        getViewState().showProgressBar();
        disposable = roomHelper.getReport(idOTF, from, unto)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reports -> {
                            ifRequestSuccess();
                            list.addAll(reports);
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

        }
    }
}
