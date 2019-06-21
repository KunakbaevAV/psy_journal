package ru.geekbrains.psy_journal.view.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatDialogFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.model.data.Catalog;
import ru.geekbrains.psy_journal.model.data.Category;
import ru.geekbrains.psy_journal.model.data.Group;
import ru.geekbrains.psy_journal.model.data.WorkForm;
import ru.geekbrains.psy_journal.presenter.EditableDialogPresenter;
import ru.geekbrains.psy_journal.presenter.Settable;
import ru.geekbrains.psy_journal.view.dialogs.adapters.EditableDialogAdapter;
import ru.geekbrains.psy_journal.view.fragment.AddWorkFragment;

import static ru.geekbrains.psy_journal.Constants.KEY_TITLE;

public class EditableDialog extends MvpAppCompatDialogFragment implements EditableDialogView {

    @InjectPresenter
    EditableDialogPresenter editableDialogPresenter;

    @BindView(R.id.recycler_dialog)
    RecyclerView recyclerView;

    private String title;
    private EditableDialogAdapter adapter;
    private Settable settable;
    private boolean wantToCloseDialog;

    public static EditableDialog newInstance(String title) {
        EditableDialog fragment = new EditableDialog();
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @ProvidePresenter
    EditableDialogPresenter providePresenter() {
        if (getArguments() != null) {
            String title = getArguments().getString(KEY_TITLE);
            this.title = title;
            EditableDialogPresenter editableDialogPresenter = new EditableDialogPresenter(title);
            App.getAppComponent().inject(editableDialogPresenter);
            editableDialogPresenter.loadData();
            return editableDialogPresenter;
        }
        return new EditableDialogPresenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() == null) return;
        AddWorkFragment fragment = (AddWorkFragment) getActivity().getSupportFragmentManager().findFragmentByTag("Tag add work");
        if (fragment != null) settable = fragment.workPresenter;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); //TODO Сделать проверку getActivity() == null
        builder
                .setTitle(title)
                .setView(createViewList())
                .setNeutralButton(R.string.add_catalog_item, (dialog, id) -> {
                })
                .setNegativeButton(R.string.exit, (dialog, id) -> getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .remove(this)
                        .commitNow());
        return builder.create();
    }

    private View createViewList() {
        if (getActivity() == null) return null;
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.editable_dialog_list, null);
        ButterKnife.bind(this, view);
        adapter = new EditableDialogAdapter(editableDialogPresenter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        return view;
    }

    private void onClickAddItem() {
        LayoutInflater factory = LayoutInflater.from(getActivity());
        @SuppressLint("InflateParams") final View alertView = factory.inflate(R.layout.layout_add_catalog_item, null);
        if (getActivity() == null) return;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(alertView);
        builder.setTitle(R.string.add_catalog_item);
        builder.setNegativeButton(R.string.exit, null);
        builder.setPositiveButton(R.string.add_catalog_item, (dialog, id) -> {
            EditText catalogItem = alertView.findViewById(R.id.new_catalog_item);
            String newCatalogItem = catalogItem.getText().toString();
            editableDialogPresenter.insertCatalogItem(newCatalogItem);
            wantToCloseDialog = true;
        });
        builder.show();
    }

    @Override
    public void loadData(String title) {
        if (title.equals(getString(R.string.choose_category))) {
            editableDialogPresenter.getCategory();
            return;
        }
        if (title.equals(getString(R.string.choose_group))) {
            editableDialogPresenter.getGroup();
            return;
        }
        if (title.equals(getString(R.string.choose_work_form))) {
            editableDialogPresenter.getWorkForm();
        }
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
    public void saveCatalogItem(Catalog catalog) {
        if (settable == null) return;
        if (catalog instanceof Category) settable.saveSelectedCategory((Category) catalog);
        if (catalog instanceof Group) settable.saveSelectedGroup((Group) catalog);
        if (catalog instanceof WorkForm) settable.saveSelectedWorkForm((WorkForm) catalog);
    }

    @Override
    public void onResume() {
        super.onResume();
        final AlertDialog dialog = (AlertDialog) getDialog();
        if (dialog != null) {
            Button neutralButton = dialog.getButton(Dialog.BUTTON_NEUTRAL);
            neutralButton.setOnClickListener(v -> {
                wantToCloseDialog = false;
                onClickAddItem();
                if (wantToCloseDialog) dialog.dismiss();
            });
        }
    }
}
