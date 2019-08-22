package ru.geekbrains.psy_journal.presentation.presenter.dialogs;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ru.geekbrains.psy_journal.data.repositories.RoomHelper;
import ru.geekbrains.psy_journal.data.repositories.model.Catalog;
import ru.geekbrains.psy_journal.data.repositories.model.Category;
import ru.geekbrains.psy_journal.data.repositories.model.Group;
import ru.geekbrains.psy_journal.data.repositories.model.WorkForm;
import ru.geekbrains.psy_journal.presentation.presenter.Insertable;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.EditableDialogView;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.viewholders.Displayed;

import static ru.geekbrains.psy_journal.Constants.ERROR_INSERTING_CATALOG_ITEM_TO_DATABASE;
import static ru.geekbrains.psy_journal.Constants.ERROR_LOADING_DATA_FROM_DATABASE;

@InjectViewState
public class EditableDialogPresenter extends MvpPresenter<EditableDialogView> implements
	Bindable,
	Insertable {

    @Inject RoomHelper roomHelper;

    private final List<Catalog> catalogList = new ArrayList<>();
    private Catalog catalog;
    private Disposable disposable;

    @Override
    public void bindView(Displayed displayed, int position) {
        Catalog catalogItem = catalogList.get(position);
        displayed.bind(catalogItem.getName());
    }

    public int getItemCount() {
        return catalogList.size();
    }

    @Override
    public void selectItem(int position) {
        getViewState().saveSelectedCatalog(catalogList.get(position));
    }

    public void getCategory() {
        disposable = roomHelper.getListCategory()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    catalogList.addAll(list);
                    getViewState().updateRecyclerView();
                        }, throwable -> getViewState().showToast(ERROR_LOADING_DATA_FROM_DATABASE + throwable.getMessage())
                );
    }

    public void getGroup() {
        disposable = roomHelper.getListGroups()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    catalogList.addAll(list);
                    getViewState().updateRecyclerView();
                        }, throwable -> getViewState().showToast(ERROR_LOADING_DATA_FROM_DATABASE + throwable.getMessage())
                );
    }

    public void getWorkForm() {
        disposable = roomHelper.getListWorkForms()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    catalogList.addAll(list);
                    getViewState().updateRecyclerView();
                        }, throwable -> getViewState().showToast(ERROR_LOADING_DATA_FROM_DATABASE + throwable.getMessage())
                );
    }

    @Override
    public void insertCategoryItem(String name) {
        catalog = new Category(name);
        insertCatalogItemSubscribe(roomHelper.insertItemCategory((Category) catalog));
    }

    @Override
    public void insertGroupItem(String name) {
        catalog = new Group(name);
        insertCatalogItemSubscribe(roomHelper.insertItemGroup((Group) catalog));
    }

    @Override
    public void insertWorkFormItem(String name) {
        catalog = new WorkForm(name);
        insertCatalogItemSubscribe(roomHelper.insertItemWorkForm((WorkForm) catalog));
    }

    private void insertCatalogItemSubscribe(Completable completable) {
        disposable = completable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            catalogList.add(catalog);
                            getViewState().updateRecyclerView();
                        },
                        throwable -> getViewState().showToast(ERROR_INSERTING_CATALOG_ITEM_TO_DATABASE + throwable.getMessage())
                );
    }

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (disposable != null) disposable.dispose();
	}
}
