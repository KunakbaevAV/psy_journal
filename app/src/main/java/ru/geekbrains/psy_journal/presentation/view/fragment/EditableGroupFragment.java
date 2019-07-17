package ru.geekbrains.psy_journal.presentation.view.fragment;

import com.arellomobile.mvp.presenter.ProvidePresenter;

import ru.geekbrains.psy_journal.data.repositories.model.Catalog;
import ru.geekbrains.psy_journal.data.repositories.model.Group;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presentation.presenter.fragments.EditableListPresenter;

public class EditableGroupFragment extends EditableCategoryFragment{

	@ProvidePresenter
	EditableListPresenter providePresenter(){
		EditableListPresenter presenter = new EditableListPresenter();
		App.getAppComponent().inject(presenter);
		presenter.getGroup();
		return presenter;
	}

	@Override
	public String getListName(){
		return "Группа";
	}

	@Override
	public void changeName(Catalog catalog, int position) {
		editableListPresenter.changeNameGroup((Group) catalog, position);
	}

	@Override
	public void remove(Catalog catalog) {
		editableListPresenter.removeGroup((Group) catalog);
	}
}
