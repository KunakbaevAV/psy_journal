package ru.geekbrains.psy_journal.view.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.arellomobile.mvp.presenter.InjectPresenter;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.model.data.Functional;
import ru.geekbrains.psy_journal.presenter.DialogFunctionPresenter;
import ru.geekbrains.psy_journal.presenter.Settable;
import ru.geekbrains.psy_journal.view.dialogs.adapters.DialogAdapter;
import ru.geekbrains.psy_journal.view.fragment.AddWorkFragment;

public abstract class FunctionDialog extends AbstractDialog implements Updated{

	protected Settable settable;
	private DialogAdapter adapter;

	@InjectPresenter DialogFunctionPresenter functionPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() == null) return;
        AddWorkFragment fragment = (AddWorkFragment) getActivity().getSupportFragmentManager().findFragmentByTag("Tag add work");
        if (fragment != null) settable = fragment.workPresenter;
	}

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
        settable = null;
    }
}
