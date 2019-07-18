package ru.geekbrains.psy_journal.presentation.presenter.fragments;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.psy_journal.data.repositories.model.Catalog;
import ru.geekbrains.psy_journal.data.repositories.model.Group;

@InjectViewState
public class EditableGroupPresenter extends EditableCatalogPresenter {

	public void getGroup() {
		getViewState().showProgressBar();
		disposable = roomHelper.getListGroups()
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(list -> {
					catalogList.addAll(list);
					ifRequestSuccess();
				},
				e -> {
					getViewState().hideProgressBar();
					Log.e("getGroup: e", e.getMessage());
				});
	}

	@Override
	protected void removeCatalog(Catalog catalog) {
		getViewState().showProgressBar();
		disposable = roomHelper.deleteItemGroup((Group) catalog)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(() -> getViewState().hideProgressBar(),
				e -> {
					getViewState().hideProgressBar();
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
					ifRequestSuccess();
				},
				e -> {
					getViewState().hideProgressBar();
					Log.e("removeGroup: e", e.getMessage());
				});
	}
}
