package ru.geekbrains.psy_journal.presentation.view.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presentation.presenter.dialogs.OpenFileDialogPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.OpenFileDialogView;
import ru.geekbrains.psy_journal.presentation.view.activities.SelectableFile;
import ru.geekbrains.psy_journal.presentation.view.dialogs.adapters.OpenFileDialogAdapter;

public class OpenFileDialog extends AbstractDialog implements
	OpenFileDialogView {

	@BindView(R.id.current_folder) TextView textView;
    @BindView(R.id.recycler_all_work)RecyclerView recyclerView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    @InjectPresenter OpenFileDialogPresenter presenter;

    private OpenFileDialogAdapter adapter;
    private SelectableFile selectableFile;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() == null) return;
        App.getAppComponent().inject(presenter);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.openDefaultDirectory();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        selectableFile = (SelectableFile) context;
    }

    @Override
    protected View createView() {
        if (getActivity() == null) return null;
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.open_file_dialog_list, null);
        ButterKnife.bind(this, view);
        adapter = new OpenFileDialogAdapter(presenter.getOpenable());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        return view;
    }

    @Override
    protected String getTitle() {
        return getResources().getString(R.string.select_file);
    }

    @Override
    public void updateRecyclerView() {
        adapter.notifyDataSetChanged();
    }

	@Override
	public void showCurrentDirectory(String path) {
		textView.setText(path);
	}

    @Override
    public void startLoadXml(File file) {
        selectableFile.getFileXML(file);
        dismiss();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }

}

