package ru.geekbrains.psy_journal.presentation.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import java.io.IOException;
import javax.inject.Inject;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ru.geekbrains.psy_journal.data.repositories.RoomHelper;
import ru.geekbrains.psy_journal.domain.file.CreatedByExcel;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.ShownMessage;

import static ru.geekbrains.psy_journal.Constants.TAG;

@InjectViewState
public class MainPresenter extends MvpPresenter<ShownMessage> {

	@Inject	RoomHelper roomHelper;
	@Inject	CreatedByExcel excel;

	private Disposable disposable;

	public void createExcelFile(){
		disposable = roomHelper.getListReportingJournal()
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(list -> {
				if (list.isEmpty()) showMessage("база пуста");
				else{
					try {
						Log.d(TAG, "createExcelFile: размер полученного списка ReportingJournal = " + list.size());
						excel.create(list);
						getViewState().showMessage("файл записан в DOCUMENTS/Reports");
					}catch (IOException e){
						getViewState().showMessage(String.format("файл не записан, есть ошибка %s", e.getMessage()));
					}
				}
			},
				e -> Log.e("createExcelFile: e", e.getMessage()));
	}

	public void showMessage(String message){
		getViewState().showMessage(message);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (disposable != null) disposable.dispose();
	}
}
