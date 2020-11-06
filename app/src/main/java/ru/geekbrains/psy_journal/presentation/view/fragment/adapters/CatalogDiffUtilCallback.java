package ru.geekbrains.psy_journal.presentation.view.fragment.adapters;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

import ru.geekbrains.psy_journal.data.repositories.model.Catalog;

public class CatalogDiffUtilCallback extends DiffUtil.Callback {

	private final List<Catalog> oldList;
	private final List<Catalog> newList;

	public CatalogDiffUtilCallback(List<Catalog> oldList, List<Catalog> newList) {
		this.oldList = oldList;
		this.newList = newList;
	}

	@Override
	public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
		Catalog oldItemCatalog = oldList.get(oldItemPosition);
		Catalog newItemCatalog = newList.get(newItemPosition);
		return oldItemCatalog.getId() == newItemCatalog.getId();
	}

	@Override
	public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
		Catalog oldItemCatalog = oldList.get(oldItemPosition);
		Catalog newItemCatalog = newList.get(newItemPosition);
		return oldItemCatalog.getName().equals(newItemCatalog.getName());
	}

	@Override
	public int getOldListSize() {
		return (oldList == null) ? 0 : oldList.size();
	}

	@Override
	public int getNewListSize() {
		return (newList == null) ? 0 : newList.size();
	}
}
