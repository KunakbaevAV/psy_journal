package ru.geekbrains.psy_journal.presentation.view.fragment;

import android.content.Context;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presentation.presenter.fragments.EditableCategoryPresenter;
import ru.geekbrains.psy_journal.presentation.view.fragment.adapters.EditableListsAdapter;
import ru.geekbrains.psy_journal.presentation.view.utilities.ItemTouchHelperCallback;

public class EditableCategoryFragment extends EditableFragment {

	@InjectPresenter EditableCategoryPresenter categoryPresenter;

	@ProvidePresenter
	EditableCategoryPresenter providePresenter(){
		EditableCategoryPresenter presenter = new EditableCategoryPresenter(null);
		App.getAppComponent().inject(presenter);
		presenter.getCategory();
		return presenter;
	}

	@Override
	public String getListName(Context context){
		return context.getResources().getString(R.string.choose_category);
	}

	protected void showRecycler() {
		super.showRecycler();
		adapter = new EditableListsAdapter(categoryPresenter.getAdapterPresenter());
		recycler.setAdapter(adapter);
        new ItemTouchHelperCallback(recycler);
	}

	@Override
	public void addCatalog(String name) {
		categoryPresenter.addCatalog(name);
	}
}
