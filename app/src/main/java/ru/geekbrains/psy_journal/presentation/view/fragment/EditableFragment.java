package ru.geekbrains.psy_journal.presentation.view.fragment;

import android.content.Context;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

import butterknife.BindView;
import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.data.repositories.model.Catalog;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.EditableView;
import ru.geekbrains.psy_journal.presentation.view.fragment.adapters.CatalogDiffUtilCallback;
import ru.geekbrains.psy_journal.presentation.view.fragment.adapters.EditableListsAdapter;

public abstract class EditableFragment extends AbstractFragment implements
	EditableView,
	Named {

	@BindView(R.id.add_catalog_item) Button addButton;
	EditableListsAdapter adapter;

	@Override
	public void updateRecyclerView() {
		adapter.notifyDataSetChanged();
	}

	@Override
	public void updateRecyclerView(List<Catalog> oldList, List<Catalog> newList) {
		recycler.post(() -> {
				CatalogDiffUtilCallback diffUtilCallback = new CatalogDiffUtilCallback(oldList, newList);
				DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilCallback, false);
				diffResult.dispatchUpdatesTo(adapter);
			});
	}

	@Override
	public void performAction(String nameCatalog) {
		String message = String.format("ошибка действия %s с БД", nameCatalog);
		Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
	}

	@Override
	public abstract String getListName(Context context);

	@Override
	protected void showRecycler() {
		super.showRecycler();
		addButton.setOnClickListener(button -> openAddDialog());
	}

	protected abstract void openAddDialog();
}
