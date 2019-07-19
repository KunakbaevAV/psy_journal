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

	/** Проверка базы, заполнена или нет
	 *
	 */
	public void checkDataBase(){
    	disposable = roomHelper.getItemOTF(1)
		    .observeOn(AndroidSchedulers.mainThread())
		    .subscribe(otf -> {
		    	if (otf == null) getViewState().showEmpty();
		    }, e -> Log.e("checkDataBase: e", e.getMessage()));
    }

	public void loadDataBase(String pathFile) throws XmlPullParserException{
		File file = new File(pathFile);
		if(file.exists()){
			FileXMLLoader xmlLoader = new FileXMLLoader(loadableDataBase);
			xmlLoader.toParseFile(file)
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe();
		}
	}

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
