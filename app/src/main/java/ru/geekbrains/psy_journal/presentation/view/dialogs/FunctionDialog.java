package ru.geekbrains.psy_journal.presentation.view.dialogs;

import android.view.LayoutInflater;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.arellomobile.mvp.presenter.InjectPresenter;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.data.repositories.model.Functional;
import ru.geekbrains.psy_journal.presentation.presenter.dialogs.DialogFunctionPresenter;
import ru.geekbrains.psy_journal.presentation.presenter.SettableByFunction;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.FunctionView;
import ru.geekbrains.psy_journal.presentation.view.dialogs.adapters.DialogAdapter;

public abstract class FunctionDialog extends AbstractDialog implements FunctionView {

	protected SettableByFunction settableByFunction;
	private DialogAdapter adapter;

	@InjectPresenter DialogFunctionPresenter functionPresenter;

	protected View createView(){
		if (getActivity() == null) return null;
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.function_dialog, null);
		RecyclerView recyclerView = view.findViewById(R.id.recycler_dialog);
		adapter = new DialogAdapter(functionPresenter);
		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		recyclerView.setHasFixedSize(true);
		return view;
	}

	protected String getTitle(){
		return functionPresenter.getTitle();
	}

	@Override
	public void update() {
		adapter.notifyDataSetChanged();
	}

    @Override
    public abstract void openNewFeature(Functional function);

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		settableByFunction = null;
	}
}
