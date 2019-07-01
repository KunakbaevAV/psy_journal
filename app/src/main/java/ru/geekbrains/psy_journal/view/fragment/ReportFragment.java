package ru.geekbrains.psy_journal.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presenter.ReportPresenter;
import ru.geekbrains.psy_journal.view.fragment.adapters.AdapterReport;

public class ReportFragment extends MvpAppCompatFragment implements ReportView{

	public static ReportFragment newInstance(int idOTF, long from, long unto) {
		ReportFragment reportFragment = new ReportFragment();
		Bundle args = new Bundle();
		args.putInt("key idOTF", idOTF);
		args.putLong("key from", from);
		args.putLong("key unto", unto);
		reportFragment.setArguments(args);
		return reportFragment;
	}

	@BindView(R.id.recycler_all_work) RecyclerView recycler;
	@BindView(R.id.progress_bar) ProgressBar progressBar;

	private Unbinder unbinder;
	private AdapterReport adapterReport;

	@InjectPresenter ReportPresenter reportPresenter;

	@ProvidePresenter ReportPresenter providePresenter(){
		ReportPresenter reportPresenter = new ReportPresenter();
		App.getAppComponent().inject(reportPresenter);
		if (getArguments() != null){
			int idOTF = getArguments().getInt("key idOTF");
			long from = getArguments().getLong("key from");
			long unto = getArguments().getLong("key unto");
			reportPresenter.initialize(idOTF, from, unto);
		}
		return reportPresenter;
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_all_work, container, false);
		unbinder = ButterKnife.bind(this, view);
		showRecycler();
		return view;
	}

	private void showRecycler() {
		recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
		recycler.setHasFixedSize(true);
		adapterReport = new AdapterReport(reportPresenter.getRecyclePresenter());
		recycler.setAdapter(adapterReport);
	}

	@Override
	public void updateRecyclerView() {
		adapterReport.notifyDataSetChanged();
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
