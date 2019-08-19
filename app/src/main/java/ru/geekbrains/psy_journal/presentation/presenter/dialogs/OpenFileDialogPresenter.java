package ru.geekbrains.psy_journal.presentation.presenter.dialogs;

import com.arellomobile.mvp.InjectViewState;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.viewholders.DisplayedFileViewHolder;

@InjectViewState
public class OpenFileDialogPresenter extends SelectFileDialogPresenter{

	@Named(Constants.DOWNLOADS)
	@Inject File defaultDirectory;

    private final Openable openable = new OpenFileAdapter();

	public Openable getOpenable() {
		return openable;
	}

	public void openDefaultDirectory() {
	    getViewState().showProgressBar();
	    showDefaultDirectory(defaultDirectory);
	    if (currentDirectory == null) return;
	    disposable = Single.fromCallable(() -> displayFiles.showFiles())
		    .subscribeOn(Schedulers.computation())
		    .observeOn(AndroidSchedulers.mainThread())
		    .subscribe(list -> {
		    	    fileList = list;
				    ifRequestSuccess();
			    }, throwable -> showError(throwable.getMessage())
		    );
    }

	protected void showCurrentDirectory() {
		currentDirectory = displayFiles.getCurrentDirectory();
		getViewState().showCurrentDirectory(currentDirectory.getPath());
	}

	protected void showError(String error){
		getViewState().hideProgressBar();
		getViewState().showCurrentDirectory(String.format("ошибка открытия директории, %s", error));
	}

    private void goToFolder(File folder) {
        getViewState().showProgressBar();
        disposable = Single.fromCallable(() -> displayFiles.goToFolder(folder))
            .subscribeOn(Schedulers.computation())
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
	        .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(list -> {
                    fileList = list;
                    showCurrentDirectory();
                    ifRequestSuccess();
                }, throwable -> showError(throwable.getMessage())
            );
	}

	private class OpenFileAdapter extends SelectFileAdapter implements Openable{

		@Override
		public void bindView(DisplayedFileViewHolder displayed, int position) {
			displayed.bind(fileList.get(position));
		}

		@Override
		public Selectable getSelectable() {
			return this;
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
				getViewState().selectFile(fileItem);
			}
		}
	}
}
