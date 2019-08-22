package ru.geekbrains.psy_journal.presentation.view.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.data.repositories.model.Catalog;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presentation.presenter.SettableByCatalog;
import ru.geekbrains.psy_journal.presentation.presenter.dialogs.EditableDialogPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.fragments.Addable;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.EditableDialogView;
import ru.geekbrains.psy_journal.presentation.view.dialogs.adapters.EditableDialogAdapter;
import ru.geekbrains.psy_journal.presentation.view.fragment.AddWorkFragment;

import static ru.geekbrains.psy_journal.Constants.KEY_TITLE;
import static ru.geekbrains.psy_journal.Constants.TAG_ADD_WORK;

public abstract class EditableDialog extends AbstractDialog implements
	EditableDialogView,
	Addable {

    @BindView(R.id.recycler_all_work) RecyclerView recyclerView;

    protected SettableByCatalog settableByCatalog;

    private EditableDialogAdapter adapter;

    @InjectPresenter
    EditableDialogPresenter editablePresenter;

    @ProvidePresenter
    EditableDialogPresenter providePresenter() {
        EditableDialogPresenter editableDialogPresenter = new EditableDialogPresenter();
        App.getAppComponent().inject(editableDialogPresenter);
        return editableDialogPresenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() == null) return;
        AddWorkFragment fragment = (AddWorkFragment) getActivity().getSupportFragmentManager()
	        .findFragmentByTag(TAG_ADD_WORK);
        if (fragment != null) settableByCatalog = fragment.workPresenter;
        hasPositiveButton(true);
        setTextPositiveBut(getResources().getString(R.string.add_catalog_item));
    }

    @Override
    protected View createView() {
        if (getActivity() == null) return null;
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_all_work, null);
        unbinder = ButterKnife.bind(this, view);
        adapter = new EditableDialogAdapter(editablePresenter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        return view;
    }

    @Override
    protected String getTitle() {
	    if (getArguments() != null){
		    return getArguments().getString(KEY_TITLE);
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
		if (getActivity() != null) {
			String title = getTitle();
			AddCatalogItemDialog dialog = AddCatalogItemDialog.newInstance(title);
			dialog.show(getActivity().getSupportFragmentManager(), Constants.TAG_ADD + title);
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
    public abstract void saveSelectedCatalog(Catalog catalog);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        settableByCatalog = null;
    }
}
