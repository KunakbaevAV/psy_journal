package ru.geekbrains.psy_journal.presentation.view.fragment;

import android.content.Context;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presentation.presenter.fragments.EditableWorkFormPresenter;
import ru.geekbrains.psy_journal.presentation.view.dialogs.AddCatalogItemDialog;
import ru.geekbrains.psy_journal.presentation.view.fragment.adapters.EditableListsAdapter;
import ru.geekbrains.psy_journal.presentation.view.utilities.ItemTouchHelperCallback;

public class EditableWorkFormFragment extends EditableFragment {

	@InjectPresenter EditableWorkFormPresenter workFormPresenter;

	@ProvidePresenter
	EditableWorkFormPresenter providePresenter(){
		EditableWorkFormPresenter presenter = new EditableWorkFormPresenter(null);
		App.getAppComponent().inject(presenter);
		presenter.getWorkForms();
		return presenter;
	}

	@Override
	public String getListName(Context context){
		return context.getResources().getString(R.string.choose_work_form);
	}

	protected void showRecycler() {
		super.showRecycler();
		adapter = new EditableListsAdapter(workFormPresenter.getAdapterPresenter());
		recycler.setAdapter(adapter);
		new ItemTouchHelperCallback(recycler);
	}

	protected void openAddDialog() {
		if (getActivity() != null) {
			String title = getListName(getActivity());
			AddCatalogItemDialog dialog = AddCatalogItemDialog.newInstance(title);
			dialog.setPresenter(workFormPresenter);
			dialog.show(getActivity().getSupportFragmentManager(), Constants.TAG_ADD + title);
		}
	}
}
