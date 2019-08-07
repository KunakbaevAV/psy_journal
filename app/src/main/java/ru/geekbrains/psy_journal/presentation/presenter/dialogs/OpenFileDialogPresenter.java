package ru.geekbrains.psy_journal.presentation.presenter.dialogs;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.domain.file.DisplayFiles;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.OpenFileDialogView;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.viewholders.DisplayedFileViewHolder;

import static ru.geekbrains.psy_journal.Constants.ERROR_LOADING_DATA_FROM_DATABASE;

@InjectViewState
public class OpenFileDialogPresenter extends MvpPresenter<OpenFileDialogView>{

    @Inject DisplayFiles displayFiles;

    private final OpenFileAdapter openFileAdapter = new OpenFileAdapter();
    private List<File> fileList;
    private File currentDirectory;
    private Disposable disposable;

	public Openable getOpenable() {
		return openFileAdapter;
	}

	public void openDefaultDirectory() {
	    getViewState().showProgressBar();
	    showCurrentDirectory();
	    if (currentDirectory == null) return;
	    disposable = Single.fromCallable(() -> displayFiles.showFiles())
		    .subscribeOn(Schedulers.io())
		    .observeOn(AndroidSchedulers.mainThread())
		    .subscribe(list -> {
				    fileList = list;
				    ifRequestSuccess();
			    }, throwable -> showError(throwable.getMessage())
		    );
    }

	private void showCurrentDirectory() {
		currentDirectory = displayFiles.getCurrentDirectory();
		getViewState().showCurrentDirectory(currentDirectory.getPath());
	}

    private void saveSelectedFilePath(File file) {
        getViewState().startLoadXml(file);
    }

    private void goToFolder(File folder) {
        getViewState().showProgressBar();
        disposable = Single.fromCallable(() -> displayFiles.goToFolder(folder))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(list -> {
                        fileList = list;
                        showCurrentDirectory();
                        ifRequestSuccess();
                    }, throwable -> showError(throwable.getMessage())
            );
    }

    private void onClickGoUp() {
        getViewState().showProgressBar();
        disposable = Single.fromCallable(() -> displayFiles.goUp(currentDirectory))
	        .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(list -> {
                    fileList = list;
                    showCurrentDirectory();
                    ifRequestSuccess();
                }, throwable -> showError(throwable.getMessage())
            );
}

    private void ifRequestSuccess() {
        getViewState().updateRecyclerView();
        getViewState().hideProgressBar();
    }

    private void showError(String error){
	    getViewState().hideProgressBar();
	    getViewState().showCurrentDirectory(String.format("%s %s", ERROR_LOADING_DATA_FROM_DATABASE, error));
    }

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (disposable != null) disposable.dispose();
	}

	private class OpenFileAdapter implements Openable{

		@Override
		public void bindView(DisplayedFileViewHolder displayed, int position) {
			displayed.bind(fileList.get(position));
		}

		@Override
		public int getItemCount() {
			if (fileList != null) {
				return fileList.size();
			}
			return 0;
		}

		@Override
		public void selectItem(int position) {
			File fileItem = fileList.get(position);
			if (fileItem.getName().equals(Constants.SUB_LEVEL)){
				onClickGoUp();
				return;
			}
			if (fileItem.isDirectory()) {
				goToFolder(fileItem);
			} else {
				saveSelectedFilePath(fileItem);
			}
		}
	}
}
