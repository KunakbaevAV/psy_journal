package ru.geekbrains.psy_journal.presentation.view.dialogs;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.presentation.presenter.SettableByFunction;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.fragments.RecycleringView;
import ru.geekbrains.psy_journal.presentation.view.dialogs.adapters.FunctionDialogAdapter;

public abstract class FunctionDialog extends AbstractDialog implements RecycleringView {

	@BindView(R.id.recycler_all_work) RecyclerView recyclerView;
	@BindView(R.id.progress_bar) ProgressBar progressBar;

	protected SettableByFunction settableByFunction;
	protected FunctionDialogAdapter adapter;

	@Override
	protected View createView(){
		if (getActivity() == null) return null;
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.fragment_all_work, null);
		unbinder = ButterKnife.bind(this, view);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		recyclerView.setHasFixedSize(true);
		return view;
	}

	public FunctionDialog setSettableByFunction(SettableByFunction settableByFunction) {
		this.settableByFunction = settableByFunction;
		return this;
	}

	@Override
	public void updateRecyclerView(){
		adapter.notifyDataSetChanged();
	}

	@Override
	public void showProgressBar(){
		progressBar.setVisibility(ProgressBar.VISIBLE);
	}

	@Override
	public void hideProgressBar(){
		progressBar.setVisibility(ProgressBar.INVISIBLE);
	}
}
