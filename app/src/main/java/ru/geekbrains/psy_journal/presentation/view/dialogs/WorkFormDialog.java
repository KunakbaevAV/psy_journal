package ru.geekbrains.psy_journal.presentation.view.dialogs;

import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presentation.presenter.fragments.EditableWorkFormPresenter;
import ru.geekbrains.psy_journal.presentation.view.fragment.adapters.EditableListsAdapter;

public class WorkFormDialog extends EditableDialog {

	@InjectPresenter EditableWorkFormPresenter workFormPresenter;

	@ProvidePresenter
	EditableWorkFormPresenter providePresenter(){
		EditableWorkFormPresenter presenter = new EditableWorkFormPresenter(settableByCatalog);
		App.getAppComponent().inject(presenter);
		presenter.getWorkForm();
		return presenter;
	}

	@Override
	protected View createView() {
		View view = super.createView();
		adapter = new EditableListsAdapter(workFormPresenter.getAdapterPresenter());
		recyclerView.setAdapter(adapter);
		return view;
	}

	@Override
	protected String getTitle() {
		return getResources().getString(R.string.choose_work_form);
	}

	@Override
	public void addCatalog(String name) {
		workFormPresenter.addCatalog(name);
	}
}
