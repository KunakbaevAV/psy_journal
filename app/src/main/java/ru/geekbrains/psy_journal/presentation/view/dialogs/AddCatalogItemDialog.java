package ru.geekbrains.psy_journal.presentation.view.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.textfield.TextInputEditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presentation.presenter.fragments.EditableCatalogPresenter;

public class AddCatalogItemDialog extends AbstractDialog {

	public static AddCatalogItemDialog newInstance(String title){
		AddCatalogItemDialog itemDialog = new AddCatalogItemDialog();
		Bundle arg = new Bundle();
		arg.putString(Constants.KEY_TITLE, title);
		itemDialog.setArguments(arg);
		return itemDialog;
	}

	@Inject	InputMethodManager imm;
    @BindView(R.id.new_catalog_item) TextInputEditText catalogItem;

    private boolean isShownKeyBoard;
	private EditableCatalogPresenter catalogPresenter;

	public void setPresenter(EditableCatalogPresenter catalogPresenter){
		this.catalogPresenter = catalogPresenter;
	}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
	    App.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        hasPositiveButton(true);
        setTextPositiveBut(getResources().getString(R.string.add_catalog_item));
    }

    @Override
    protected View createView() {
        if (getActivity() == null) return null;
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.item_add_catalog, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public String getTitle(Context context) {
    	if (getArguments() != null){
    		return getArguments().getString(Constants.KEY_TITLE);
	    }
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
        final AlertDialog dialog = (AlertDialog) getDialog();
        if (dialog != null) {
        	if (catalogPresenter == null) dialog.dismiss();
            Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(v -> {
                onClickAddItem();
            });
            catalogItem.post(() -> {
	            isShownKeyBoard = imm.showSoftInput(catalogItem, InputMethodManager.SHOW_FORCED);
	            setListenerKeyBoard();
            });
        }
    }

    private void onClickAddItem() {
	    Editable editable = catalogItem.getText();
    	if (editable == null || "".contentEquals(editable)) return;
        catalogPresenter.addCatalog(catalogItem.getText().toString());
	    dismiss();
    }

	private void setListenerKeyBoard(){
		catalogItem.setOnEditorActionListener((v, actionId, event) -> {
			if (actionId == EditorInfo.IME_ACTION_DONE) {
				onClickAddItem();
				return true;
			}
			return false;
		});
	}

	@Override
	public void dismiss() {
		if (isShownKeyBoard){
			imm.hideSoftInputFromWindow(catalogItem.getWindowToken(), 0);
		}
		super.dismiss();
	}
}
