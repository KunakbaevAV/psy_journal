package ru.geekbrains.psy_journal.presentation.view.dialogs;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.data.repositories.model.Functional;
import ru.geekbrains.psy_journal.presentation.presenter.dialogs.DialogFunctionPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.SettableByFunction;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.FunctionView;
import ru.geekbrains.psy_journal.presentation.view.dialogs.adapters.DialogAdapter;

public abstract class FunctionDialog extends AbstractDialog implements FunctionView {

	@BindView(R.id.recycler_all_work) RecyclerView recyclerView;
	@BindView(R.id.progress_bar) ProgressBar progressBar;

	protected SettableByFunction settableByFunction;
	private DialogAdapter adapter;
	private Unbinder unbinder;

	@InjectPresenter DialogFunctionPresenter functionPresenter;

	protected View createView(){
		if (getActivity() == null) return null;
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.fragment_all_work, null);
		unbinder = ButterKnife.bind(this, view);
		adapter = new DialogAdapter(functionPresenter);
		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		recyclerView.setHasFixedSize(true);
		return view;
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

    @Override
    public abstract void openNewFeature(Functional function);

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		settableByFunction = null;
		unbinder.unbind();
	}
}
