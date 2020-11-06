package ru.geekbrains.psy_journal.presentation.presenter.fragments;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.psy_journal.data.repositories.StorableWorkForm;
import ru.geekbrains.psy_journal.data.repositories.model.Catalog;
import ru.geekbrains.psy_journal.data.repositories.model.WorkForm;
import ru.geekbrains.psy_journal.presentation.presenter.SettableByCatalog;

import static ru.geekbrains.psy_journal.Constants.ERROR_INSERTING_CATALOG_ITEM_TO_DATABASE;
import static ru.geekbrains.psy_journal.Constants.TAG;

@InjectViewState
public class EditableWorkFormPresenter extends EditableCatalogPresenter {

	@Inject StorableWorkForm storableWorkForm;

	public EditableWorkFormPresenter(SettableByCatalog settableByCatalog) {
		this.settableByCatalog = settableByCatalog;
	}

	public EditableCatalogPresenter.AdapterPresenter getAdapterPresenter() {
		final boolean isEditable = settableByCatalog == null;
		return  adapterPresenter = new EditableCatalogPresenter.AdapterPresenter(isEditable){
			@Override
			public void selectItem(int position) {
				if (!isEditable){
					transferCatalog((WorkForm)catalogList.get(position));
				}
			}
		};
	}

	private void transferCatalog(WorkForm workForm){
		settableByCatalog.saveSelectedWorkForm(workForm);
		getViewState().performAction(null);
	}

	public void getWorkForm() {
		getViewState().showProgressBar();
		disposable = storableWorkForm.getListWorkForms()
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(list -> {
					catalogList.addAll(list);
					ifRequestSuccess();
				},
				e -> {
					getViewState().hideProgressBar();
					getViewState().performAction(e.getMessage());
					Log.e("getWorkForm: e", e.getMessage());
				});
	}

	@Override
	protected void removeCatalog(Catalog catalog) {
		getViewState().showProgressBar();
		disposable = storableWorkForm.deleteItemWorkForm((WorkForm) catalog)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(() -> ifRequestSuccess(oldCatalogList, catalogList),
				e -> {
					getViewState().hideProgressBar();
					getViewState().performAction(catalog.getName());
					Log.e("removeWorkForm: e", e.getMessage());
				});
	}

	@Override
	protected void changeNameCatalog(Catalog catalog, int pos) {
		WorkForm workForm = (WorkForm) catalog;
		getViewState().showProgressBar();
		disposable = storableWorkForm.updateItemWorkForm(workForm)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(() -> {
					catalogList.set(pos, workForm);
					ifRequestSuccess(oldCatalogList, catalogList);
				},
				e -> {
					getViewState().hideProgressBar();
					getViewState().performAction(workForm.getName());
					Log.e("changeNameWorkForm: e", e.getMessage());
				});
	}

	@Override
	public void addCatalog(String name) {
		getViewState().showProgressBar();
		disposable = storableWorkForm.getAddedWorkFormItem(name)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(workForm -> {
					if (adapterPresenter.isEditable()){
						editCatalogList(workForm);
					} else {
						transferCatalog(workForm);
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
