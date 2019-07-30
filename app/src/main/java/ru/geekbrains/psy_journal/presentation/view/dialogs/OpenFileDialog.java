package ru.geekbrains.psy_journal.presentation.view.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presentation.presenter.dialogs.OpenFileDialogPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.OpenFileDialogView;
import ru.geekbrains.psy_journal.presentation.view.dialogs.adapters.OpenFileDialogAdapter;

public class OpenFileDialog extends AbstractDialog implements OpenFileDialogView {

    @BindView(R.id.recycler_dialog)
    RecyclerView recyclerView;
    @InjectPresenter
    OpenFileDialogPresenter presenter;
    private OpenFileDialogAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() == null) return;
//        hasPositiveButton(true); // TODO Уточнить, нужна ли кнопка
//        setTextButton(getResources().getString(R.string.open_file));
    }

    @Override
    public void onStart() {
        super.onStart();
        App.getAppComponent().inject(presenter);
        presenter.onStart();
    }

    @Override
    protected View createView() {
        if (getActivity() == null) return null;
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.open_file_dialog_list, null);
        ButterKnife.bind(this, view);
        adapter = new OpenFileDialogAdapter(presenter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        return view;
    }

    @Override
    protected String getTitle() {
        return getResources().getString(R.string.open_file);
    }

    @Override
    public void updateRecyclerView() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showToast(String message) {
        if (getActivity() == null) return;
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}

