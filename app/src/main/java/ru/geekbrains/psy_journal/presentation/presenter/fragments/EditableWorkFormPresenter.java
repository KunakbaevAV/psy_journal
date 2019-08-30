package ru.geekbrains.psy_journal.presentation.presenter.fragments;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.psy_journal.data.repositories.model.Catalog;
import ru.geekbrains.psy_journal.data.repositories.model.WorkForm;
import ru.geekbrains.psy_journal.presentation.presenter.SettableByCatalog;

import static ru.geekbrains.psy_journal.Constants.DB_ADD_ERROR;
import static ru.geekbrains.psy_journal.Constants.TAG;

@InjectViewState
public class EditableWorkFormPresenter extends EditableCatalogPresenter {

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

	public void getWorkForms() {
		getViewState().showProgressBar();
		disposable = roomHelper.getWorkForms()
			.observeOn(AndroidSchedulers.mainThread())
				.subscribe(workForms -> {
							catalogList.addAll(workForms);
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
		disposable = roomHelper.deleteItemWorkForm((WorkForm) catalog)
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
		disposable = roomHelper.updateItemWorkForm(workForm)
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
		disposable = roomHelper.getAddedWorkFormItem(name)
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
					Log.e(TAG, DB_ADD_ERROR + throwable.getMessage());
				}
			);
	}
}
