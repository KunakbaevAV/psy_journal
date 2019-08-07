package ru.geekbrains.psy_journal.presentation.presenter.activity;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ru.geekbrains.psy_journal.data.files.FileXMLLoader;
import ru.geekbrains.psy_journal.data.files.LoadableDataBase;
import ru.geekbrains.psy_journal.data.repositories.RoomHelper;
import ru.geekbrains.psy_journal.domain.file.CreatedByExcel;
import ru.geekbrains.psy_journal.domain.models.ReportingJournal;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.activity.InformedView;

@InjectViewState
public class MainPresenter extends MvpPresenter<InformedView> {

	@Inject RoomHelper roomHelper;
    @Inject CreatedByExcel excel;
    @Inject LoadableDataBase loadableDataBase;

    private Disposable disposable;
    private File file;

    public void takeFile(File file){
    	this.file = file;
    }

    public void saveOldDataBase(String nameReport){
		createExcelFile(nameReport);
    }

    public void updateWithoutSaving(){
    	clearDataBase();
    }

    private void clearDataBase(){
    	if (file == null) return;
		clearOTFTable();
	}

	private void clearOTFTable() {
		disposable = roomHelper.deleteAllOTF()
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(this::clearTFTable,
						e -> getViewState().showStatusClearDatabase("Ошибка при очистке БД"));
	}

	private void clearTFTable() {
		disposable = roomHelper.deleteAllTF()
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(this::clearTDTable,
						e -> getViewState().showStatusClearDatabase("Ошибка при очистке БД"));
	}

	private void clearTDTable() {
		disposable = roomHelper.deleteAllTD()
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(this::clearJournalTable,
						e -> getViewState().showStatusClearDatabase("Ошибка при очистке БД"));
	}

	private void clearJournalTable() {
		disposable = roomHelper.deleteAllJournal()
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(() -> {
							loadDataBase();
							getViewState().showStatusClearDatabase("База данных очищена");
						},
						e -> getViewState().showStatusClearDatabase("Ошибка при очистке БД"));
	}

	private void loadDataBase(){
		if(file.exists()){
			try {
				FileXMLLoader xmlLoader = new FileXMLLoader(loadableDataBase);
				disposable = xmlLoader.toParseFile(file)
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(() -> {
							getViewState().showStatusLoadDataBase(null);
							file = null;
						},
						e -> getViewState().showStatusLoadDataBase(e.getMessage()));
			} catch (XmlPullParserException e) {
				getViewState().showStatusReadXml(file.getName(), e.getDetail().getMessage());
			}
		}
	}

    public void createExcelFile(String nameReport) {
        disposable = roomHelper.getListReportingJournal()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(list -> getReport(list, nameReport),
                e -> Log.e("createExcelFile: e", e.getMessage()));
    }

    private void getReport(List<ReportingJournal> list, String nameReport){
	    if (list.isEmpty()){
	    	getViewState().showStatusWriteReport(null, null);
	    	if (this.file != null){
	    		loadDataBase();
		    }
	    } else {
			writeReportFile(list, nameReport);
	    }
    }

    private void writeReportFile(List<ReportingJournal> list, String nameReport){
	    disposable = excel.create(list, nameReport)
		    .observeOn(AndroidSchedulers.mainThread())
		    .subscribe(file -> {
				    getViewState().showStatusWriteReport(file.getName(), null);
				    if (this.file != null){
					    clearDataBase();
				    }
			    },
			    e -> getViewState().showStatusWriteReport(null, e.getMessage()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) disposable.dispose();
    }
}
