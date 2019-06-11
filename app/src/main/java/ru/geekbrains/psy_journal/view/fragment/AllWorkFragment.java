package ru.geekbrains.psy_journal.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presenter.AllWorkPresenter;

public class AllWorkFragment extends MvpAppCompatFragment implements AllWorkView {

	@Inject
	Context context;
	@BindView(R.id.recycler_all_work)
	RecyclerView recycler;
	@BindView(R.id.progress_bar)
	ProgressBar progressBar;
	@InjectPresenter
	AllWorkPresenter allWorkPresenter;
	private AdapterAllWork adapterAllWork;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_all_work, container, false);
		ButterKnife.bind(this, view);
		context = view.getContext();
		App.getAppComponent().inject(allWorkPresenter);
		showAllWorkRecycler();
		return view;
	}

	private void showAllWorkRecycler() {
		recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
		recycler.setHasFixedSize(true);
		adapterAllWork = new AdapterAllWork(allWorkPresenter.getRecyclerAllWorkPresenter());
		recycler.setAdapter(adapterAllWork);
	}

	@Override
	public void updateRecyclerView() {
		adapterAllWork.notifyDataSetChanged();
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
	public void showToast(String message) {
		if (getActivity() == null) return;
		Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
	}

}
