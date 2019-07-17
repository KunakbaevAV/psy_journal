package ru.geekbrains.psy_journal.presentation.view.fragment;

import com.arellomobile.mvp.presenter.ProvidePresenter;

import ru.geekbrains.psy_journal.data.repositories.model.Catalog;
import ru.geekbrains.psy_journal.data.repositories.model.WorkForm;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presentation.presenter.fragments.EditableListPresenter;

public class EditableWorkFormFragment extends EditableCategoryFragment {

	@ProvidePresenter
	EditableListPresenter providePresenter(){
		EditableListPresenter presenter = new EditableListPresenter();
		App.getAppComponent().inject(presenter);
		presenter.getWorkForm();
		return presenter;
	}

	@Override
	public String getListName(){
		return "Форма работы";
	}

	@Override
	public void changeName(Catalog catalog, int position) {
		editableListPresenter.changeNameWorkForm((WorkForm) catalog, position);
	}

	@Override
	public void remove(Catalog catalog) {
		editableListPresenter.removeWorkForm((WorkForm) catalog);
	}
}
