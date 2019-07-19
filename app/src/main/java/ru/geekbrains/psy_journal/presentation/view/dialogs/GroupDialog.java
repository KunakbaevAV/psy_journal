package ru.geekbrains.psy_journal.presentation.view.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.data.repositories.model.Catalog;
import ru.geekbrains.psy_journal.data.repositories.model.Group;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presentation.presenter.dialogs.EditableDialogPresenter;
import ru.geekbrains.psy_journal.presentation.view.fragment.GivenBySettableCatalog;

public class GroupDialog extends EditableDialog {

    @InjectPresenter
    EditableDialogPresenter editablePresenter;
    private boolean wantToCloseDialog;

    public static GroupDialog newInstance(String tag) {
        GroupDialog fragment = new GroupDialog();
        Bundle args = new Bundle();
        args.putString(Constants.KEY_TAG, tag);
        fragment.setArguments(args);
        return fragment;
    }

    @ProvidePresenter
    EditableDialogPresenter providePresenter() {
        EditableDialogPresenter editableDialogPresenter = new EditableDialogPresenter(getString(R.string.choose_group));
        App.getAppComponent().inject(editableDialogPresenter);
        editableDialogPresenter.getGroup();
        return editableDialogPresenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() == null) return;
        if (getArguments() != null) {
            String tag = getArguments().getString(Constants.KEY_TAG);
            GivenBySettableCatalog bySettableCatalog = (GivenBySettableCatalog) getActivity().getSupportFragmentManager().findFragmentByTag(tag);
            if (bySettableCatalog != null)
                settableByCatalog = bySettableCatalog.getSettableByCatalog();
        }
    }

    @Override
    public void saveSelectedCatalog(Catalog catalog) {
        settableByCatalog.saveSelectedGroup((Group) catalog);
        dismiss();
    }

    private void onClickAddItem() {
        LayoutInflater factory = LayoutInflater.from(getActivity());
        @SuppressLint("InflateParams") final View alertView = factory.inflate(R.layout.layout_add_catalog_item, null);
        if (getActivity() == null) return;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(alertView);
        builder.setTitle(getString(R.string.title_add_catalog_item) + " " + getString(R.string.choose_group));
        builder.setNegativeButton(R.string.cancel, null);
        builder.setPositiveButton(R.string.add_catalog_item, (dialog, id) -> {
            EditText catalogItem = alertView.findViewById(R.id.new_catalog_item);
            String newCatalogItem = catalogItem.getText().toString();
            editablePresenter.insertGroupItem(newCatalogItem);
        });
        builder.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        final AlertDialog dialog = (AlertDialog) getDialog();
        if (dialog != null) {
            Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(v -> {
                wantToCloseDialog = false;
                onClickAddItem();
                if (wantToCloseDialog) dialog.dismiss();
            });
        }
    }
}
