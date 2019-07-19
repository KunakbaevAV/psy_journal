package ru.geekbrains.psy_journal.presentation.view.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.data.repositories.model.Catalog;
import ru.geekbrains.psy_journal.data.repositories.model.Category;
import ru.geekbrains.psy_journal.data.repositories.model.Group;
import ru.geekbrains.psy_journal.data.repositories.model.WorkForm;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presentation.presenter.SettableByCatalog;
import ru.geekbrains.psy_journal.presentation.presenter.dialogs.EditableDialogPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.EditableDialogView;
import ru.geekbrains.psy_journal.presentation.view.dialogs.adapters.EditableDialogAdapter;
import ru.geekbrains.psy_journal.presentation.view.fragment.AddWorkFragment;

import static ru.geekbrains.psy_journal.Constants.KEY_TITLE;
import static ru.geekbrains.psy_journal.Constants.TAG_ADD_WORK;

public abstract class EditableDialog extends AbstractDialog implements EditableDialogView {

    @BindView(R.id.recycler_dialog)
    RecyclerView recyclerView;

    protected SettableByCatalog settableByCatalog;

    private EditableDialogAdapter adapter;

    @InjectPresenter
    EditableDialogPresenter editablePresenter;

    @ProvidePresenter
    EditableDialogPresenter providePresenter() {
        if (getArguments() != null) {
            String title = getArguments().getString(KEY_TITLE);
            EditableDialogPresenter editableDialogPresenter = new EditableDialogPresenter(title);
            App.getAppComponent().inject(editableDialogPresenter);
            return editableDialogPresenter;
        }
        return new EditableDialogPresenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() == null) return;
        AddWorkFragment fragment = (AddWorkFragment) getActivity().getSupportFragmentManager().findFragmentByTag(TAG_ADD_WORK);
        if (fragment != null) settableByCatalog = fragment.workPresenter;
        hasPositiveButton(true);
        setTextButton(getResources().getString(R.string.add_catalog_item));
    }

    @Override
    protected View createView() {
        if (getActivity() == null) return null;
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.editable_dialog_list, null);
        ButterKnife.bind(this, view);
        adapter = new EditableDialogAdapter(editablePresenter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        return view;
    }

    @Override
    protected String getTitle() {
        return editablePresenter.getTitle();
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

    @Override
    public void saveSelectedCategory(Category catalog) {
        if (settableByCatalog == null) return;
        settableByCatalog.saveSelectedCategory(catalog);
        if (getDialog() != null) getDialog().dismiss();
    }

    @Override
    public void saveSelectedGroup(Group catalog) {
        if (settableByCatalog == null) return;
        settableByCatalog.saveSelectedGroup(catalog);
        if (getDialog() != null) getDialog().dismiss();
    }

    @Override
    public void saveSelectedWorkForm(WorkForm catalog) {
        if (settableByCatalog == null) return;
        settableByCatalog.saveSelectedWorkForm(catalog);
        if (getDialog() != null) getDialog().dismiss();
    }

    @Override
    public abstract void saveSelectedCatalog(Catalog catalog);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        settableByCatalog = null;
    }
}
