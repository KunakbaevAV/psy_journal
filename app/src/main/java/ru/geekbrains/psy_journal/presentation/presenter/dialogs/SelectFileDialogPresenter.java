package ru.geekbrains.psy_journal.presentation.presenter.dialogs;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.domain.file.DisplayFiles;
import ru.geekbrains.psy_journal.domain.file.FileProvider;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.FileSelectionDialogView;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.viewholders.DisplayedSelectedFileViewHolder;
import ru.geekbrains.psy_journal.presentation.view.utilities.BooleanCollection;

@InjectViewState
public class SelectFileDialogPresenter  extends MvpPresenter<FileSelectionDialogView> {

	@Named(Constants.REPORTS)
	@Inject	File defaultDirectory;

	protected List<File> fileList;
	protected DisplayFiles displayFiles;
	protected File currentDirectory;
	protected Disposable disposable;

	private final Selectable selectable = new SelectFileAdapter();
	private final BooleanCollection booleanArray = new BooleanCollection();

	public Selectable getSelectable() {
		return selectable;
	}

	public void openDefaultDirectory() {
		getViewState().showProgressBar();
		showDefaultDirectory(defaultDirectory);
		if (currentDirectory == null) return;
		disposable = Single.fromCallable(() -> displayFiles.showRootDirectory(currentDirectory))
			.subscribeOn(Schedulers.computation())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(list -> {
					fileList = list;
					fillBooleanFile();
					ifRequestSuccess();
				}, throwable -> showError(throwable.getMessage())
			);
	}

	protected void showDefaultDirectory(File defaultDirectory){
		if (defaultDirectory == null) return;
		displayFiles = new FileProvider(defaultDirectory);
		showCurrentDirectory();
	}

	protected void showCurrentDirectory() {
		currentDirectory = displayFiles.getCurrentDirectory();
	}

	protected void showError(String error){
		getViewState().hideProgressBar();
		getViewState().showCurrentDirectory(error);
	}

	public List<File> getReportFiles(){
		List<File> list = new ArrayList<>();
		for (int i = 0; i < booleanArray.size(); i++) {
			if (booleanArray.valueAt(i)){
				list.add(fileList.get(i));
			}
		}
		return list;
	}

	public void deleteReportFiles(){
		for (int i = 0; i < booleanArray.size(); i++) {
			if (booleanArray.valueAt(i)){
				if (fileList.get(i).isFile()){
					fileList.get(i).delete();
				}
			}
		}
	}

	protected void ifRequestSuccess() {
		getViewState().updateRecyclerView();
		getViewState().hideProgressBar();
	}

	private void fillBooleanFile(){
		if (booleanArray.size() != 0) booleanArray.clear();
		for (int i = 0; i < fileList.size(); i++) {
			booleanArray.append(i, false);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (disposable != null) disposable.dispose();
	}

	protected class SelectFileAdapter implements Selectable {

		@Override
		public void bindView(DisplayedSelectedFileViewHolder displayed, int position) {
			displayed.bind(fileList.get(position), booleanArray.get(position));
		}

		@Override
		public int getItemCount() {
			return (fileList != null) ? fileList.size() : 0;
		}

		@Override
		public void selectItem(int position) {
			boolean isSelected = booleanArray.valueAt(position);
			booleanArray.put(position, !isSelected);
		}
	}
}
