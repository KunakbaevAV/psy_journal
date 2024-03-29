package ru.geekbrains.psy_journal.presentation.presenter.fragments;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.psy_journal.data.repositories.StorableGroup;
import ru.geekbrains.psy_journal.data.repositories.model.Catalog;
import ru.geekbrains.psy_journal.data.repositories.model.Group;
import ru.geekbrains.psy_journal.presentation.presenter.SettableByCatalog;

import static ru.geekbrains.psy_journal.Constants.ERROR_INSERTING_CATALOG_ITEM_TO_DATABASE;
import static ru.geekbrains.psy_journal.Constants.TAG;

@InjectViewState
public class EditableGroupPresenter extends EditableCatalogPresenter {

	@Inject	StorableGroup storableGroup;

	public EditableGroupPresenter(SettableByCatalog settableByCatalog) {
		this.settableByCatalog = settableByCatalog;
	}

	public EditableCatalogPresenter.AdapterPresenter getAdapterPresenter() {
		final boolean isEditable = settableByCatalog == null;
		return  adapterPresenter = new EditableCatalogPresenter.AdapterPresenter(isEditable){
			@Override
			public void selectItem(int position) {
				if (!isEditable){
					transferCatalog((Group)catalogList.get(position));
				}
			}
		};
	}

	private void transferCatalog(Group group){
		settableByCatalog.saveSelectedGroup(group);
		getViewState().performAction(null);
	}

	public void getGroup() {
		getViewState().showProgressBar();
		disposable = storableGroup.getListGroups()
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(list -> {
					catalogList.addAll(list);
					ifRequestSuccess();
				},
				e -> {
					getViewState().hideProgressBar();
					getViewState().performAction(e.getMessage());
					Log.e("getGroup: e", e.getMessage());
				});
	}

	@Override
	protected void removeCatalog(Catalog catalog) {
		getViewState().showProgressBar();
		disposable = storableGroup.deleteItemGroup((Group) catalog)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(() -> ifRequestSuccess(oldCatalogList, catalogList),
				e -> {
					getViewState().hideProgressBar();
					getViewState().performAction(catalog.getName());
					Log.e("removeGroup: e", e.getMessage());
				});
	}

	@Override
	protected void changeNameCatalog(Catalog catalog, int pos) {
		Group group = (Group) catalog;
		getViewState().showProgressBar();
		disposable = storableGroup.updateItemGroup(group)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(() -> {
					catalogList.set(pos, group);
					ifRequestSuccess(oldCatalogList, catalogList);
				},
				e -> {
					getViewState().hideProgressBar();
					getViewState().performAction(group.getName());
					Log.e("changeNameGroup: e", e.getMessage());
				});
	}

	@Override
	public void addCatalog(String name) {
		getViewState().showProgressBar();
		disposable = storableGroup.getAddedGroupItem(name)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(group -> {
					if (adapterPresenter.isEditable()){
						editCatalogList(group);
					} else {
						transferCatalog(group);
					}
				},
				throwable -> {
					getViewState().hideProgressBar();
					getViewState().performAction(name);
					Log.e(TAG, ERROR_INSERTING_CATALOG_ITEM_TO_DATABASE + throwable.getMessage());
				}
			);
	}
}
