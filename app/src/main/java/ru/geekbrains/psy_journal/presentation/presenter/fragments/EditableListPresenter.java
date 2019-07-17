package ru.geekbrains.psy_journal.presentation.presenter.fragments;

import android.util.Log;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ru.geekbrains.psy_journal.data.repositories.RoomHelper;
import ru.geekbrains.psy_journal.data.repositories.model.Catalog;
import ru.geekbrains.psy_journal.data.repositories.model.Category;
import ru.geekbrains.psy_journal.data.repositories.model.Group;
import ru.geekbrains.psy_journal.data.repositories.model.WorkForm;
import ru.geekbrains.psy_journal.presentation.presenter.Editable;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.viewholders.Displayed;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.fragments.ChangableView;

@InjectViewState
public class EditableListPresenter extends MvpPresenter<ChangableView> {

	@Inject RoomHelper roomHelper;

	private final List<Catalog> catalogList = new ArrayList<>();
	private final AdapterPresenter adapterPresenter = new AdapterPresenter();
	private Disposable disposable;

	public AdapterPresenter getAdapterPresenter() {
		return adapterPresenter;
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

	public void removeCategory(Category category) {
		getViewState().showProgressBar();
		disposable = roomHelper.deleteItemCategory(category)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(() -> getViewState().hideProgressBar(),
				e -> {
					getViewState().hideProgressBar();
					Log.e("removeCategory: e", e.getMessage());
				});
	}

	public void removeGroup(Group group) {
		getViewState().showProgressBar();
		disposable = roomHelper.deleteItemGroup(group)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(() -> getViewState().hideProgressBar(),
				e -> {
					getViewState().hideProgressBar();
					Log.e("removeGroup: e", e.getMessage());
				});
	}

	public void removeWorkForm(WorkForm workForm) {
		getViewState().showProgressBar();
		disposable = roomHelper.deleteItemWorkForm(workForm)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(() -> getViewState().hideProgressBar(),
				e -> {
					getViewState().hideProgressBar();
					Log.e("removeWorkForm: e", e.getMessage());
				});
	}

	public void changeNameCategory(Category category, int pos) {
		getViewState().showProgressBar();
		disposable = roomHelper.updateItemCategory(category)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(() -> {
					catalogList.set(pos, category);
					ifRequestSuccess();
				},
				e -> {
					getViewState().hideProgressBar();
					Log.e("removeWorkForm: e", e.getMessage());
				});
	}

	public void changeNameGroup(Group group, int pos) {
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

	public void changeNameWorkForm(WorkForm workForm, int pos) {
		getViewState().showProgressBar();
		disposable = roomHelper.updateItemWorkForm(workForm)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(() -> {
					catalogList.set(pos, workForm);
					ifRequestSuccess();
				},
				e -> {
					getViewState().hideProgressBar();
					Log.e("removeWorkForm: e", e.getMessage());
				});
	}

	private void ifRequestSuccess() {
		getViewState().updateRecyclerView();
		getViewState().hideProgressBar();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (disposable != null) disposable.dispose();
	}

	private class AdapterPresenter implements Editable {

		@Override
		public void bindView(Displayed displayed, int position) {
			Catalog catalogItem = catalogList.get(position);
			displayed.bind(null, catalogItem.getName());
		}

		@Override
		public int getItemCount() {
			return catalogList.size();
		}

		@Override
		public void selectItem(int position) {

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
			getViewState().changeName(catalog, position);
		}

		@Override
		public void delete(int position) {
			Catalog catalog = catalogList.remove(position);
			getViewState().remove(catalog);
		}
	}
}
