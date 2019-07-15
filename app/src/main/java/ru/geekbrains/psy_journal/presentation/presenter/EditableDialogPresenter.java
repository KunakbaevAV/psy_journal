package ru.geekbrains.psy_journal.presentation.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.psy_journal.data.repositories.RoomHelper;
import ru.geekbrains.psy_journal.data.repositories.model.Catalog;
import ru.geekbrains.psy_journal.data.repositories.model.Category;
import ru.geekbrains.psy_journal.data.repositories.model.Group;
import ru.geekbrains.psy_journal.data.repositories.model.WorkForm;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.Displayed;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.EditableDialogView;

import static ru.geekbrains.psy_journal.Constants.ERROR_INSERTING_CATALOG_ITEM_TO_DATABASE;
import static ru.geekbrains.psy_journal.Constants.ERROR_LOADING_DATA_FROM_DATABASE;

@InjectViewState
public class EditableDialogPresenter extends MvpPresenter<EditableDialogView> implements Bindable, Insertable {

    private static final String EMPTY_CODE = "";

    @Inject
    RoomHelper roomHelper;

    private List<Catalog> catalogList;
    private String title;
    private Catalog catalog;

    public EditableDialogPresenter() {

    }

    public EditableDialogPresenter(String title) {
        this.title = title;
        App.getAppComponent().inject(this);
        catalogList = new ArrayList<>();
    }

    @Override
    public void bindView(Displayed displayed, int position) {
        Catalog catalogItem = catalogList.get(position);
        displayed.bind(EMPTY_CODE, catalogItem.getName());
    }

    public int getItemCount() {
        if (catalogList != null) {
            return catalogList.size();
        }
        return 0;
    }

    @Override
    public void selectItem(int position) {
        getViewState().saveSelectedCatalog(catalogList.get(position));
    }

    @SuppressLint("CheckResult")
    public void getCategory() {
        roomHelper.getListCategory()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    catalogList.addAll(list);
                    getViewState().updateRecyclerView();
                        }, throwable -> getViewState().showToast(ERROR_LOADING_DATA_FROM_DATABASE + throwable.getMessage())
                ).isDisposed();
    }

    @SuppressLint("CheckResult")
    public void getGroup() {
        roomHelper.getListGroups()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    catalogList.addAll(list);
                    getViewState().updateRecyclerView();
                        }, throwable -> getViewState().showToast(ERROR_LOADING_DATA_FROM_DATABASE + throwable.getMessage())
                ).isDisposed();
    }

    @SuppressLint("CheckResult")
    public void getWorkForm() {
        roomHelper.getListWorkForms()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    catalogList.addAll(list);
                    getViewState().updateRecyclerView();
                        }, throwable -> getViewState().showToast(ERROR_LOADING_DATA_FROM_DATABASE + throwable.getMessage())
                ).isDisposed();
    }

    @Override
    public void insertCategoryItem(String name) {
        catalog = new Category();
        catalog.setName(name);
        insertCatalogItemSubscribe(roomHelper.insertItemCategory((Category) catalog));
    }

    @Override
    public void insertGroupItem(String name) {
        catalog = new Group();
        catalog.setName(name);
        insertCatalogItemSubscribe(roomHelper.insertItemGroup((Group) catalog));
    }

    @Override
    public void insertWorkFormItem(String name) {
        catalog = new WorkForm();
        catalog.setName(name);
        insertCatalogItemSubscribe(roomHelper.insertItemWorkForm((WorkForm) catalog));
    }

    @SuppressLint("CheckResult")
    private void insertCatalogItemSubscribe(Completable completable) {
        completable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            catalogList.add(catalog);
                            getViewState().updateRecyclerView();
                        },
                        throwable -> getViewState().showToast(ERROR_INSERTING_CATALOG_ITEM_TO_DATABASE + throwable.getMessage())
                ).isDisposed();
    }

    public String getTitle() {
        return title;
    }
}
