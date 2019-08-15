package ru.geekbrains.psy_journal.presentation.presenter.fragments;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.psy_journal.data.repositories.model.Catalog;
import ru.geekbrains.psy_journal.data.repositories.model.WorkForm;

import static ru.geekbrains.psy_journal.Constants.ERROR_INSERTING_CATALOG_ITEM_TO_DATABASE;
import static ru.geekbrains.psy_journal.Constants.TAG;

@InjectViewState
public class EditableWorkFormPresenter extends EditableCatalogPresenter {

	public void getWorkForm() {
		getViewState().showProgressBar();
		disposable = roomHelper.getListWorkForms()
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(list -> {
					catalogList.addAll(list);
					ifRequestSuccess();
				},
				e -> {
					getViewState().hideProgressBar();
					Log.e("getWorkForm: e", e.getMessage());
				});
	}

	@Override
	protected void removeCatalog(Catalog catalog) {
		getViewState().showProgressBar();
		disposable = roomHelper.deleteItemWorkForm((WorkForm) catalog)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(() -> getViewState().hideProgressBar(),
				e -> {
					getViewState().hideProgressBar();
					Log.e("removeWorkForm: e", e.getMessage());
				});
	}

	@Override
	protected void changeNameCatalog(Catalog catalog, int pos) {
		WorkForm workForm = (WorkForm) catalog;
		getViewState().showProgressBar();
		disposable = roomHelper.updateItemWorkForm(workForm)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(() -> {
					catalogList.set(pos, workForm);
					ifRequestSuccess();
				},
				e -> {
					getViewState().hideProgressBar();
					Log.e("changeNameWorkForm: e", e.getMessage());
				});
	}

	@Override
	public void addCatalog(String name) {
		WorkForm workForm = new WorkForm();
		workForm.setName(name);
		getViewState().showProgressBar();
		disposable = roomHelper.insertItemWorkForm(workForm)
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(
						() -> {
							catalogList.add(workForm);
							ifRequestSuccess();
						},
						throwable -> {
							getViewState().hideProgressBar();
							Log.e(TAG, ERROR_INSERTING_CATALOG_ITEM_TO_DATABASE + throwable.getMessage());
						}
				);
	}
}
