package ru.geekbrains.psy_journal.presentation.view.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.geekbrains.psy_journal.BuildConfig;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presentation.presenter.dialogs.SelectFileDialogPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.FileSelectionDialogView;
import ru.geekbrains.psy_journal.presentation.view.activities.SelectableFile;
import ru.geekbrains.psy_journal.presentation.view.dialogs.adapters.SelectionFileDialogAdapter;

public class FileSelectionDialog extends AbstractDialog implements
	FileSelectionDialogView {

	@BindView(R.id.current_folder) TextView textView;
	@BindView(R.id.recycler_all_work) RecyclerView recyclerView;
	@BindView(R.id.progress_bar) ProgressBar progressBar;

	@InjectPresenter SelectFileDialogPresenter presenter;

	protected SelectableFile selectableFile;
	protected RecyclerView.Adapter adapter;

	@ProvidePresenter SelectFileDialogPresenter providePresenter(){
		SelectFileDialogPresenter presenter = new SelectFileDialogPresenter();
		App.getAppComponent().inject(presenter);
		presenter.openDefaultDirectory();
		adapter = new SelectionFileDialogAdapter(presenter.getSelectable());
		return presenter;
	}

	@Override
	protected View createView() {
		if (getActivity() == null) return null;
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.open_file_dialog_list, null);
		unbinder = ButterKnife.bind(this, view);
		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		recyclerView.setHasFixedSize(true);
		initButton();
		return view;
	}

	private void initButton(){
		hasPositiveButton(true);
		setTextPositiveBut("Отправить");
		hasNegativeButton(true);
		setTextNegativeBut(getString(R.string.delete));
	}

	@Override
	protected String getTitle() {
		return "Выберите файлы отчета";
	}

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		selectableFile = (SelectableFile) context;
	}

	@Override
	public void onResume() {
		super.onResume();
		final AlertDialog dialog = (AlertDialog) getDialog();
		if (dialog != null) {
			Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
			positiveButton.setOnClickListener(v -> {
				selectableFile.selectReportFiles(getUriList(dialog.getContext(),presenter.getReportFiles()));
				dismiss();
			});
			Button negativeButton = dialog.getButton(Dialog.BUTTON_NEGATIVE);
			negativeButton.setOnClickListener(v -> {
				presenter.deleteReportFiles();
				dismiss();
			});
		}
	}

	private ArrayList<Uri> getUriList(Context context, List<File> fileList){
		if (fileList.isEmpty()) return null;
		ArrayList<Uri> uris = new ArrayList<>(fileList.size());
		for (int i = 0; i < fileList.size(); i++) {
			uris.add(FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID, fileList.get(i)));
		}
		return uris;
	}

	@Override
	public void updateRecyclerView() {
		adapter.notifyDataSetChanged();
	}

	@Override
	public void showCurrentDirectory(String error) {
		textView.setText(String.format("ошибка открытия папки \"Отчеты\", %s", error));
	}

	@Override
	public void selectFile(File file) {}

	@Override
	public void showProgressBar() {
		progressBar.setVisibility(ProgressBar.VISIBLE);
	}

	@Override
	public void hideProgressBar() {
		progressBar.setVisibility(ProgressBar.INVISIBLE);
	}

}
