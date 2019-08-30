package ru.geekbrains.psy_journal.presentation.presenter.fragments;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.psy_journal.data.repositories.model.Catalog;
import ru.geekbrains.psy_journal.data.repositories.model.Category;
import ru.geekbrains.psy_journal.presentation.presenter.SettableByCatalog;

import static ru.geekbrains.psy_journal.Constants.DB_ADD_ERROR;
import static ru.geekbrains.psy_journal.Constants.TAG;

@InjectViewState
public class EditableCategoryPresenter extends EditableCatalogPresenter {

	public EditableCategoryPresenter(SettableByCatalog settableByCatalog) {
		this.settableByCatalog = settableByCatalog;
	}

	public EditableCatalogPresenter.AdapterPresenter getAdapterPresenter() {
		final boolean isEditable = settableByCatalog == null;
		return  adapterPresenter = new EditableCatalogPresenter.AdapterPresenter(isEditable){
			@Override
			public void selectItem(int position) {
				if (!isEditable) {
					transferCatalog((Category) catalogList.get(position));
				}
			}
		};
	}

	private void transferCatalog(Category category){
		settableByCatalog.saveSelectedCategory(category);
		getViewState().performAction(null);
	}

	public void getCategories() {
		getViewState().showProgressBar();
		disposable = roomHelper.getCategories()
			.observeOn(AndroidSchedulers.mainThread())
				.subscribe(categories -> {
							catalogList.addAll(categories);
					ifRequestSuccess();
				},
				e -> {
					getViewState().hideProgressBar();
					getViewState().performAction(e.getMessage());
					Log.e("getCategory: e", e.getMessage());
				});
	}

	@Override
	protected void removeCatalog(Catalog catalog) {
		getViewState().showProgressBar();
		disposable = roomHelper.deleteItemCategory((Category) catalog)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(() -> ifRequestSuccess(oldCatalogList, catalogList),
				e -> {
					getViewState().hideProgressBar();
					getViewState().performAction(catalog.getName());
					Log.e("removeCategory: e", e.getMessage());
				});
	}

	@Override
	protected void changeNameCatalog(Catalog catalog, int pos) {
		Category category = (Category) catalog;
		getViewState().showProgressBar();
		disposable = roomHelper.updateItemCategory(category)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(() -> {
					catalogList.set(pos, category);
					ifRequestSuccess(oldCatalogList, catalogList);
				},
				e -> {
					getViewState().hideProgressBar();
					getViewState().performAction(category.getName());
					Log.e("changeNameCategory: e", e.getMessage());
				});
	}

	@Override
	public void addCatalog(String name) {
		getViewState().showProgressBar();
		disposable = roomHelper.getAddedCategoryItem(name)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(category -> {
					if (adapterPresenter.isEditable()){
						editCatalogList(category);
					} else {
						transferCatalog(category);
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
