package ru.geekbrains.psy_journal.presentation.presenter.fragments;

import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import ru.geekbrains.psy_journal.data.repositories.RoomHelper;
import ru.geekbrains.psy_journal.data.repositories.model.Catalog;
import ru.geekbrains.psy_journal.presentation.presenter.Editable;
import ru.geekbrains.psy_journal.presentation.presenter.SettableByCatalog;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.EditableView;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.viewholders.Displayed;

public abstract class EditableCatalogPresenter extends MvpPresenter<EditableView> {

	@Inject RoomHelper roomHelper;

	final List<Catalog> catalogList = new ArrayList<>();
	EditableCatalogPresenter.AdapterPresenter adapterPresenter;
	SettableByCatalog settableByCatalog;
	Disposable disposable;

	protected abstract void removeCatalog(Catalog catalog);

	protected abstract void changeNameCatalog(Catalog catalog, int pos);

	public abstract void addCatalog(String name);

	void ifRequestSuccess() {
		getViewState().updateRecyclerView();
		getViewState().hideProgressBar();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (disposable != null) disposable.dispose();
	}

	protected class AdapterPresenter implements Editable {

		private final boolean isEditable;

		AdapterPresenter(boolean isEditable) {
			this.isEditable = isEditable;
		}

		@Override
		public void bindView(Displayed displayed, int position) {
			Catalog catalogItem = catalogList.get(position);
            displayed.bind(catalogItem.getName());
		}

		@Override
		public int getItemCount() {
			return catalogList.size();
		}

		@Override
		public void selectItem(int position) {}

		@Override
		public boolean isEditable() {
			return isEditable;
		}

		@Override
		public void selectItem(String name, int position) {
			if (name == null){
				getViewState().updateRecyclerView();
				return;
			}
			Catalog catalog = catalogList.get(position);
			if (catalog.getName().equals(name)) return;
			catalog.setName(name);
			changeNameCatalog(catalog, position);
		}

		@Override
		public void delete(int position) {
			removeCatalog(catalogList.remove(position));
		}
	}
}
