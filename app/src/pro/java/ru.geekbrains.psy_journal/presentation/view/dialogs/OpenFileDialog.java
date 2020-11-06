package ru.geekbrains.psy_journal.presentation.view.dialogs;

import android.content.Context;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.io.File;

import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presentation.presenter.dialogs.OpenFileDialogPresenter;
import ru.geekbrains.psy_journal.presentation.view.dialogs.adapters.OpenFileDialogAdapter;

public class OpenFileDialog extends FileSelectionDialog {

    @InjectPresenter OpenFileDialogPresenter presenter;

    @ProvidePresenter OpenFileDialogPresenter providePresenter(){
	    OpenFileDialogPresenter presenter = new OpenFileDialogPresenter();
	    App.getAppComponent().inject(presenter);
	    presenter.openDefaultDirectory();
	    adapter = new OpenFileDialogAdapter(presenter.getOpenable());
	    return presenter;
    }

	protected void initButton(){
		hasPositiveButton(false);
		hasNegativeButton(false);
	}

    @Override
    public String getTitle(Context context) {
        return context.getString(R.string.select_file);
    }

	@Override
	public void showCurrentDirectory(String path) {
		textView.setText(path);
	}

    @Override
    public void selectFile(File file) {
        selectableFile.selectFileXML(file);
        dismiss();
    }
}

