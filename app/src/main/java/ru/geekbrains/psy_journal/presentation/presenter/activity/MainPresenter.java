package ru.geekbrains.psy_journal.presentation.presenter.activity;

import android.util.Log;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.psy_journal.data.repositories.RoomHelper;
import ru.geekbrains.psy_journal.domain.file.CreatedByExcel;
import ru.geekbrains.psy_journal.domain.models.ReportingJournal;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.activity.InformedView;

@InjectViewState
public class MainPresenter extends MvpPresenter<InformedView> {

    @Inject RoomHelper roomHelper;
    @Inject CreatedByExcel excel;

    private Disposable disposable;

    public void createExcelFile() {
        disposable = roomHelper.getListReportingJournal()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::writeReportFile,
                e -> Log.e("createExcelFile: e", e.getMessage()));
    }

    private void writeReportFile(List<ReportingJournal> list){
	    if (list.isEmpty()) getViewState().showEmpty();
	    else {
			disposable = excel.create(list)
		        .observeOn(AndroidSchedulers.mainThread())
		        .subscribe(file -> getViewState().showGood(file.getName()),
			        e -> getViewState().showBad(e.getMessage()));
	    }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) disposable.dispose();
    }
}
