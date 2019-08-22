package ru.geekbrains.psy_journal.presentation.view.dialogs;

import android.os.Bundle;

import androidx.annotation.Nullable;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.data.repositories.model.Catalog;
import ru.geekbrains.psy_journal.data.repositories.model.Category;
import ru.geekbrains.psy_journal.presentation.presenter.dialogs.EditableDialogPresenter;
import ru.geekbrains.psy_journal.presentation.view.fragment.GivenBySettableCatalog;

public class CategoryDialog extends EditableDialog {

    public static CategoryDialog newInstance(String tag) {
        CategoryDialog fragment = new CategoryDialog();
        Bundle args = new Bundle();
        args.putString(Constants.KEY_TAG, tag);
        fragment.setArguments(args);
        return fragment;
    }

    @ProvidePresenter
    EditableDialogPresenter providePresenter() {
	    EditableDialogPresenter presenter = super.providePresenter();
        presenter.getCategory();
        return presenter;
    }

	@Override
	protected String getTitle() {
		return getResources().getString(R.string.choose_category);
	}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() == null) return;
        if (getArguments() != null) {
            String tag = getArguments().getString(Constants.KEY_TAG);
            GivenBySettableCatalog bySettableCatalog = (GivenBySettableCatalog) getActivity().getSupportFragmentManager().findFragmentByTag(tag);
            if (bySettableCatalog != null)
                settableByCatalog = bySettableCatalog.getSettableByCatalog();
        }
    }

    @Override
    public void saveSelectedCatalog(Catalog catalog) {
        settableByCatalog.saveSelectedCategory((Category) catalog);
    }

	@Override
	public void addCatalog(String name) {
		editablePresenter.insertCategoryItem(name);
	}
}
