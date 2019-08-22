package ru.geekbrains.psy_journal.presentation.view.fragment;

import android.content.Context;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.presentation.presenter.fragments.Addable;
import ru.geekbrains.psy_journal.presentation.view.dialogs.AddCatalogItemDialog;
import ru.geekbrains.psy_journal.presentation.view.fragment.adapters.EditableListsAdapter;

public abstract class EditableFragment extends AbstractFragment implements
	Named,
	Addable {

	protected EditableListsAdapter adapter;

	@Override
	public void updateRecyclerView() {
		adapter.notifyDataSetChanged();
	}

	@Override
	public abstract String getListName(Context context);

	@OnClick(R.id.add_catalog_item)
	void openAddDialog() {
		if (getActivity() != null) {
			String title = getListName(getActivity());
			AddCatalogItemDialog dialog = AddCatalogItemDialog.newInstance(title);
			dialog.show(getActivity().getSupportFragmentManager(), Constants.TAG_ADD + title);
		}
	}
}
