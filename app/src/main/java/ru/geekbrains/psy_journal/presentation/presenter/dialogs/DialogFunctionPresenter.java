package ru.geekbrains.psy_journal.presentation.presenter.dialogs;

import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import ru.geekbrains.psy_journal.data.repositories.model.Functional;
import ru.geekbrains.psy_journal.presentation.presenter.SettableByFunction;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.viewholders.Displayed;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.fragments.RecycleringView;

public abstract class DialogFunctionPresenter extends MvpPresenter<RecycleringView> implements Bindable {

	protected final List<Functional> list = new ArrayList<>();
	protected final SettableByFunction settableByFunction;
	protected Disposable disposable;

	protected DialogFunctionPresenter(SettableByFunction settableByFunction) {
		this.settableByFunction = settableByFunction;
	}

	protected void ifRequestSuccess() {
		getViewState().updateRecyclerView();
		getViewState().hideProgressBar();
	}

	@Override
	public void bindView(Displayed displayed, int position) {
		Functional function = list.get(position);
        displayed.bind(String.format("%s. %s", function.getCode(), function.getName()));
	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (disposable != null) disposable.dispose();
	}
}
