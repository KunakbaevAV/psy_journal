package ru.geekbrains.psy_journal.presentation.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.fragments.RecycleringView;

public abstract class AbstractReportingFragment extends MvpAppCompatFragment implements RecycleringView {

	@BindView(R.id.recycler_all_work) RecyclerView recycler;
	@BindView(R.id.progress_bar) ProgressBar progressBar;

	protected Unbinder unbinder;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_all_work, container, false);
		unbinder = ButterKnife.bind(this, view);
		showRecycler();
		return view;
	}

	void showRecycler() {
		recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
		recycler.setHasFixedSize(true);
	}

	@Override
	public void updateRecyclerView() {

	}

	@Override
	public void showProgressBar() {
		progressBar.setVisibility(ProgressBar.VISIBLE);
	}

	@Override
	public void hideProgressBar() {
		progressBar.setVisibility(ProgressBar.INVISIBLE);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}
}
