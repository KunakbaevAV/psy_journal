package ru.geekbrains.psy_journal.presentation.view.fragment;

import androidx.recyclerview.widget.ItemTouchHelper;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presentation.presenter.fragments.EditableCategoryPresenter;
import ru.geekbrains.psy_journal.presentation.view.fragment.adapters.EditableListsAdapter;
import ru.geekbrains.psy_journal.presentation.view.utilities.ItemTouchHelperCallback;

public class EditableCategoryFragment extends EditableCatalogFragment{

	@InjectPresenter EditableCategoryPresenter presenter;

	@ProvidePresenter
	EditableCategoryPresenter providePresenter(){
		EditableCategoryPresenter presenter = new EditableCategoryPresenter();
		App.getAppComponent().inject(presenter);
		presenter.getCategory();
		return presenter;
	}

	@Override
	public String getListName(){
		return "Категория";
	}

	protected void showRecycler() {
		super.showRecycler();
		adapter = new EditableListsAdapter(presenter.getAdapterPresenter());
		recycler.setAdapter(adapter);
		new ItemTouchHelper(new ItemTouchHelperCallback(adapter)).attachToRecyclerView(recycler);
	}
}
