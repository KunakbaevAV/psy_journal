package ru.geekbrains.psy_journal.presentation.view.dialogs;

import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presentation.presenter.fragments.EditableCategoryPresenter;
import ru.geekbrains.psy_journal.presentation.view.fragment.adapters.EditableListsAdapter;

public class CategoryDialog extends EditableDialog {

	@InjectPresenter EditableCategoryPresenter categoryPresenter;

	@ProvidePresenter
	EditableCategoryPresenter providePresenter(){
		EditableCategoryPresenter presenter = new EditableCategoryPresenter(settableByCatalog);
		App.getAppComponent().inject(presenter);
		presenter.getCategory();
		return presenter;
	}

	@Override
	protected View createView() {
		View view = super.createView();
		adapter = new EditableListsAdapter(categoryPresenter.getAdapterPresenter());
		recyclerView.setAdapter(adapter);
		return view;
	}

	@Override
	protected String getTitle() {
		return getResources().getString(R.string.choose_category);
	}

	protected void onClickAddItem() {
		if (getActivity() != null) {
			String title = getTitle();
			AddCatalogItemDialog dialog = AddCatalogItemDialog.newInstance(title);
			dialog.setPresenter(categoryPresenter);
			dialog.show(getActivity().getSupportFragmentManager(), Constants.TAG_ADD + title);
		}
	}
}
