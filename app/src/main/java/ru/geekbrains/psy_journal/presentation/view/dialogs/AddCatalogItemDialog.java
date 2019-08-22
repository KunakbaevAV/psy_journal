package ru.geekbrains.psy_journal.presentation.view.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.presentation.presenter.fragments.Addable;

public class AddCatalogItemDialog extends AbstractDialog {

	public static AddCatalogItemDialog newInstance(String title){
		AddCatalogItemDialog itemDialog = new AddCatalogItemDialog();
		Bundle arg = new Bundle();
		arg.putString(Constants.KEY_TITLE, title);
		itemDialog.setArguments(arg);
		return itemDialog;
	}

    @BindView(R.id.new_catalog_item) EditText catalogItem;

	private Addable addable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() == null) return;
        hasPositiveButton(true);
        setTextPositiveBut(getResources().getString(R.string.add_catalog_item));
    }

    @Override
    protected View createView() {
        if (getActivity() == null) return null;
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_add_catalog_item, null);
        unbinder = ButterKnife.bind(this, view);
        addable = getAddable();
        return view;
    }

    private Addable getAddable(){
    	if (getFragmentManager() == null) return null;
    	List<Fragment> fragments = getFragmentManager().getFragments();
    	int index = fragments.lastIndexOf(this);
    	return (Addable) fragments.get(index - 1);
    }

    @Override
    protected String getTitle() {
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
            Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(v -> {
                onClickAddItem();
                dialog.dismiss();
            });
        }
    }

    private void onClickAddItem() {
    	if (addable == null) return;
        addable.addCatalog(catalogItem.getText().toString());
    }
}
