package ru.geekbrains.psy_journal.presentation.presenter;

import android.util.Log;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ru.geekbrains.psy_journal.data.repositories.RoomHelper;
import ru.geekbrains.psy_journal.domain.file.CreatedByExcel;
import ru.geekbrains.psy_journal.domain.models.ReportingJournal;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.Informed;

@InjectViewState
public class MainPresenter extends MvpPresenter<Informed> {

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
		    try {
			    File file = excel.create(list);
			    getViewState().showGood(file.getName());
		    } catch (IOException e) {
			    getViewState().showBad(e.getMessage());
		    }
	    }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) disposable.dispose();
    }
}
