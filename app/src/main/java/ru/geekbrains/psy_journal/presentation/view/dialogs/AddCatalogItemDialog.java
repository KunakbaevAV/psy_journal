package ru.geekbrains.psy_journal.presentation.view.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.presentation.presenter.fragments.Addable;

public class AddCatalogItemDialog extends AbstractDialog {

    @BindView(R.id.new_catalog_item)
    EditText catalogItem;

    private Addable addable;

    public AddCatalogItemDialog(Addable addable) {
        this.addable = addable;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() == null) return;
        hasPositiveButton(true);
        setTextButton(getResources().getString(R.string.add_catalog_item));
    }

    @Override
    protected View createView() {
        if (getActivity() == null) return null;
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_add_catalog_item, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected String getTitle() {
        if (getActivity() == null) {
            return "";
        } else {
            return getActivity().getResources().getString(R.string.title_add_catalog_item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        final AlertDialog dialog = (AlertDialog) getDialog();
        if (dialog != null) {
            Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(v -> {
                onClickAddItem();
                dialog.dismiss();
            });
        }
    }

    private void onClickAddItem() {
        addable.addCatalog(catalogItem.getText().toString());
    }
}
