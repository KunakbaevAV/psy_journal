package ru.geekbrains.psy_journal.presentation.view.fragment;

import android.content.Context;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presentation.presenter.fragments.EditableGroupPresenter;
import ru.geekbrains.psy_journal.presentation.view.fragment.adapters.EditableListsAdapter;
import ru.geekbrains.psy_journal.presentation.view.utilities.ItemTouchHelperCallback;

public class EditableGroupFragment extends EditableFragment {

	@InjectPresenter EditableGroupPresenter presenter;

	@ProvidePresenter
	EditableGroupPresenter providePresenter(){
		EditableGroupPresenter presenter = new EditableGroupPresenter();
		App.getAppComponent().inject(presenter);
		presenter.getGroup();
		return presenter;
	}

	@Override
	public String getListName(Context context){
		return context.getResources().getString(R.string.choose_group);
	}

	protected void showRecycler() {
		super.showRecycler();
		adapter = new EditableListsAdapter(presenter.getAdapterPresenter());
		recycler.setAdapter(adapter);
		new ItemTouchHelperCallback(recycler);
	}

	@Override
	public void addCatalog(String name) {
		presenter.addCatalog(name);
	}
}
