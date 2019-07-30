package ru.geekbrains.psy_journal.presentation.presenter.dialogs;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.presentation.presenter.Derivable;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.OpenFileDialogView;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.viewholders.IOpenFileViewHolder;

@InjectViewState
public class OpenFileDialogPresenter extends MvpPresenter<OpenFileDialogView> implements Derivable {

    @Inject
    Context context;
    private List<File> fileList;
    private Drawable fileImage;
    private Drawable folderImage;

    public void onStart() {
        fileList = new ArrayList<>();
        getFiles();
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
            getFiles(fileItem);
        } else {
            saveSelectedFilePath(fileItem);
        }
    }

    private void saveSelectedFilePath(File file) {
        // TODO Метод для дальнейшей работы с выбранным файлом
    }

    private void getFiles() {
        // TODO Метод получения списка файлов
        File folderTest = new File("D://");
        File fileTest = new File("D://ProfessionalStandarts_509.xml");
        fileList.add(folderTest);
        fileList.add(fileTest);
    }

    private void getFiles(File folder) {
        // TODO Метод получения списка файлов
    }
}
