package ru.geekbrains.psy_journal.presentation.presenter.activity;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ru.geekbrains.psy_journal.data.repositories.RoomHelper;
import ru.geekbrains.psy_journal.domain.file.CreatedByExcel;
import ru.geekbrains.psy_journal.domain.models.ReportingJournal;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.InformedView;

@InjectViewState
public class MainPresenter extends MvpPresenter<InformedView> {

	@Inject	RoomHelper roomHelper;
    @Inject CreatedByExcel excel;

    private Disposable disposable;

    public void createExcelFile(String nameReport) {
        disposable = roomHelper.getListReportingJournal()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(list -> getReport(list, nameReport),
                e -> getViewState().showStatusWriteReport(null, e.getMessage()));
    }

    private void getReport(List<ReportingJournal> list, String nameReport){
	    if (list.isEmpty()){
	    	getViewState().showStatusWriteReport(null, null);
	    } else {
			writeReportFile(list, nameReport);
	    }
    }

    private void writeReportFile(List<ReportingJournal> list, String nameReport){
	    disposable = excel.create(list, nameReport)
		    .observeOn(AndroidSchedulers.mainThread())
		    .subscribe(file -> getViewState().showStatusWriteReport(file.getName(), null),
			    e -> getViewState().showStatusWriteReport(null, e.getMessage()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) disposable.dispose();
    }
}
