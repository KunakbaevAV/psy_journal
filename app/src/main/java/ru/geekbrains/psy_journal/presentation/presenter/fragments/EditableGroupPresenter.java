package ru.geekbrains.psy_journal.presentation.presenter.fragments;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.psy_journal.data.repositories.model.Catalog;
import ru.geekbrains.psy_journal.data.repositories.model.Group;
import ru.geekbrains.psy_journal.presentation.presenter.SettableByCatalog;

import static ru.geekbrains.psy_journal.Constants.DB_ADD_ERROR;
import static ru.geekbrains.psy_journal.Constants.TAG;

@InjectViewState
public class EditableGroupPresenter extends EditableCatalogPresenter {

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

	public void getGroups() {
		getViewState().showProgressBar();
		disposable = roomHelper.getGroups()
			.observeOn(AndroidSchedulers.mainThread())
				.subscribe(groups -> {
							catalogList.addAll(groups);
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
		disposable = roomHelper.deleteItemGroup((Group) catalog)
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
		disposable = roomHelper.updateItemGroup(group)
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
		disposable = roomHelper.getAddedGroupItem(name)
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
					Log.e(TAG, DB_ADD_ERROR + throwable.getMessage());
				}
			);
	}
}
