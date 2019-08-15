package ru.geekbrains.psy_journal.presentation.view.fragment;

import ru.geekbrains.psy_journal.presentation.presenter.view_ui.fragments.Named;
import ru.geekbrains.psy_journal.presentation.view.fragment.adapters.EditableListsAdapter;

public abstract class EditableCatalogFragment extends AbstractCatalogFragment implements Named {

	protected EditableListsAdapter adapter;

	@Override
	public void updateRecyclerView() {
		adapter.notifyDataSetChanged();
	}

	@Override
	public abstract String getListName();
}
