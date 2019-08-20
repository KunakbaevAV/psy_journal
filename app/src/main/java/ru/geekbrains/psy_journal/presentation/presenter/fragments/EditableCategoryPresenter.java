package ru.geekbrains.psy_journal.presentation.presenter.fragments;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.psy_journal.data.repositories.model.Catalog;
import ru.geekbrains.psy_journal.data.repositories.model.Category;

import static ru.geekbrains.psy_journal.Constants.ERROR_INSERTING_CATALOG_ITEM_TO_DATABASE;
import static ru.geekbrains.psy_journal.Constants.TAG;

@InjectViewState
public class EditableCategoryPresenter extends EditableCatalogPresenter {

	private final String title;

	public EditableCategoryPresenter(String title) {
		this.title = title;
	}

	public void getCategory() {
		getViewState().showProgressBar();
		disposable = roomHelper.getListCategory()
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(list -> {
					catalogList.addAll(list);
					ifRequestSuccess();
				},
				e -> {
					getViewState().hideProgressBar();
					Log.e("getCategory: e", e.getMessage());
				});
	}

	@Override
	protected void removeCatalog(Catalog catalog) {
		getViewState().showProgressBar();
		disposable = roomHelper.deleteItemCategory((Category) catalog)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(() -> getViewState().hideProgressBar(),
				e -> {
					getViewState().hideProgressBar();
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
					ifRequestSuccess();
				},
				e -> {
					getViewState().hideProgressBar();
					Log.e("changeNameCategory: e", e.getMessage());
				});
	}

	@Override
	public void addCatalog(String name) {
		Category category = new Category(name);
		getViewState().showProgressBar();
		disposable = roomHelper.insertItemCategory(category)
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(
						() -> {
							catalogList.add(category);
							ifRequestSuccess();
						},
						throwable -> {
							getViewState().hideProgressBar();
							Log.e(TAG, ERROR_INSERTING_CATALOG_ITEM_TO_DATABASE + throwable.getMessage());
						}
				);
	}

	@Override
	public String getTitle() {
		return title;
	}

}
