package ru.geekbrains.psy_journal.presentation.view.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.data.repositories.model.Catalog;
import ru.geekbrains.psy_journal.presentation.presenter.SettableByCatalog;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.EditableView;
import ru.geekbrains.psy_journal.presentation.view.fragment.adapters.CatalogDiffUtilCallback;
import ru.geekbrains.psy_journal.presentation.view.fragment.adapters.EditableListsAdapter;

public abstract class EditableDialog extends AbstractDialog implements
	EditableView {

    @BindView(R.id.recycler_all_work) RecyclerView recyclerView;
	@BindView(R.id.progress_bar) ProgressBar progressBar;

	SettableByCatalog settableByCatalog;
	EditableListsAdapter adapter;

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
        View view = inflater.inflate(R.layout.fragment_all_work, null);
        unbinder = ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        return view;
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

	protected abstract void onClickAddItem();

	public EditableDialog setSettableByCatalog(SettableByCatalog settableByCatalog){
		this.settableByCatalog = settableByCatalog;
		return this;
	}

    @Override
    public void updateRecyclerView() {
        adapter.notifyDataSetChanged();
    }

	@Override
	public void showProgressBar() {
		progressBar.setVisibility(View.VISIBLE);
	}

	@Override
	public void hideProgressBar() {
		progressBar.setVisibility(View.INVISIBLE);
	}

	@Override
	public void updateRecyclerView(List<Catalog> oldList, List<Catalog> newList) {
		CatalogDiffUtilCallback diffUtilCallback = new CatalogDiffUtilCallback(oldList, newList);
		DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilCallback, false);
		diffResult.dispatchUpdatesTo(adapter);
	}

	@Override
	public void performAction(String nameCatalog) {
    	if (nameCatalog != null){
    		String message = String.format("ошибка действия %s с БД", nameCatalog);
		    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
	    }
		this.dismiss();
	}
}
