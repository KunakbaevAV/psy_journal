package ru.geekbrains.psy_journal.presentation.view.dialogs;

import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presentation.presenter.fragments.EditableGroupPresenter;
import ru.geekbrains.psy_journal.presentation.view.fragment.adapters.EditableListsAdapter;

public class GroupDialog extends EditableDialog {

	@InjectPresenter EditableGroupPresenter groupPresenter;

	@ProvidePresenter
	EditableGroupPresenter providePresenter(){
		EditableGroupPresenter presenter = new EditableGroupPresenter(settableByCatalog);
		App.getAppComponent().inject(presenter);
		presenter.getGroup();
		return presenter;
	}

	@Override
	protected View createView() {
		View view = super.createView();
		adapter = new EditableListsAdapter(groupPresenter.getAdapterPresenter());
		recyclerView.setAdapter(adapter);
		return view;
	}

	@Override
	protected String getTitle() {
		return getResources().getString(R.string.choose_group);
	}

	protected void onClickAddItem() {
		if (getActivity() != null) {
			String title = getTitle();
			AddCatalogItemDialog dialog = AddCatalogItemDialog.newInstance(title);
			dialog.setPresenter(groupPresenter);
			dialog.show(getActivity().getSupportFragmentManager(), Constants.TAG_ADD + title);
		}
	}
}
