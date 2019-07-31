package ru.geekbrains.psy_journal.presentation.presenter.dialogs;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.domain.file.DisplayFiles;
import ru.geekbrains.psy_journal.presentation.presenter.Derivable;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.OpenFileDialogView;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.viewholders.IOpenFileViewHolder;

import static ru.geekbrains.psy_journal.Constants.ERROR_LOADING_DATA_FROM_DATABASE;

@InjectViewState
public class OpenFileDialogPresenter extends MvpPresenter<OpenFileDialogView> implements Derivable {

    @Inject
    Context context;

    @Inject
    DisplayFiles displayFiles;
    @Inject
    File currentDirectory;

    private List<File> fileList;
    private Drawable fileImage;
    private Drawable folderImage;


    public void onStart() {
        fileList = new ArrayList<>();
        showFiles();
        fileImage = context.getDrawable(R.drawable.ic_file_24dp);
        folderImage = context.getDrawable(R.drawable.ic_folder_24dp);
    }

    public void bindView(IOpenFileViewHolder viewHolder, int position) {
        File fileItem = fileList.get(position);
        Drawable image;
        if (fileItem.isDirectory()) {
            image = folderImage;
        } else {
            image = fileImage;
        }
        viewHolder.bind(image, fileItem.getName());
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
        if (fileItem.isDirectory()) {
            goToFolder(fileItem);
            currentDirectory = fileItem;
        } else {
            saveSelectedFilePath(fileItem);
        }
    }

    private void saveSelectedFilePath(File file) {
        getViewState().startLoadXml(file);
    }

    private void showFiles() {
        getViewState().showProgressBar();
        Single.fromCallable(() -> displayFiles.showFiles())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                            fileList.addAll(list);
                            ifRequestSuccess();
                        }, throwable -> getViewState().showToast(ERROR_LOADING_DATA_FROM_DATABASE + throwable.getMessage())
                ).isDisposed();
    }

    private void goToFolder(File folder) {
        getViewState().showProgressBar();
        Single.fromCallable(() -> displayFiles.goToFolder(folder))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                            fileList.addAll(list);
                            ifRequestSuccess();
                        }, throwable -> getViewState().showToast(ERROR_LOADING_DATA_FROM_DATABASE + throwable.getMessage())
                ).isDisposed();
    }

    public void onClickGoUp() {
        getViewState().showProgressBar();
        Single.fromCallable(() -> displayFiles.goUp(currentDirectory))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                            fileList.addAll(list);
                            ifRequestSuccess();
                        }, throwable -> getViewState().showToast(ERROR_LOADING_DATA_FROM_DATABASE + throwable.getMessage())
                ).isDisposed();
    }

    private void ifRequestSuccess() {
        getViewState().updateRecyclerView();
        getViewState().hideProgressBar();
    }
}
