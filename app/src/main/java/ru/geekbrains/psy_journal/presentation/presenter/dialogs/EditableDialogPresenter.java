package ru.geekbrains.psy_journal.presentation.presenter.dialogs;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.data.repositories.model.Catalog;
import ru.geekbrains.psy_journal.data.repositories.model.Category;
import ru.geekbrains.psy_journal.data.repositories.model.Group;
import ru.geekbrains.psy_journal.data.repositories.model.WorkForm;
import ru.geekbrains.psy_journal.data.repositories.RoomHelper;
import ru.geekbrains.psy_journal.presentation.view.dialogs.factory.CatalogFactory;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.EditableDialogView;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs.viewholders.IViewHolderCatalog;

import static ru.geekbrains.psy_journal.Constants.ERROR_INSERTING_CATALOG_ITEM_TO_DATABASE;
import static ru.geekbrains.psy_journal.Constants.ERROR_LOADING_DATA_FROM_DATABASE;

@InjectViewState
public class EditableDialogPresenter extends MvpPresenter<EditableDialogView> {

    @Inject
    CatalogFactory catalogFactory;
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

    public void bindView(IViewHolderCatalog holder) {
        Catalog catalogItem = catalogList.get(holder.getPos());
        holder.setCatalogItem(catalogItem.getName());
    }

    public int getItemCount() {
        if (catalogList != null) {
            return catalogList.size();
        }
        return 0;
    }

    public void loadData() {
        getViewState().loadData(title);
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

    //Метод для выбора элемента
    public void selectItem(IViewHolderCatalog holder) {
        Catalog catalog = catalogList.get(holder.getPos());
        if (catalog instanceof Category) {
            getViewState().saveSelectedCategory((Category) catalog);
            return;
        }
        if (catalog instanceof Group) {
            getViewState().saveSelectedGroup((Group) catalog);
            return;
        }
        if (catalog instanceof WorkForm) {
            getViewState().saveSelectedWorkForm((WorkForm) catalog);
        }
    }

    //Метод для добавления нового элемента каталога
    public void insertCatalogItem(String name) {
        catalog = catalogFactory.getCatalog(title);
        if (catalog == null) return;
        catalog.setName(name);

        if (catalog instanceof Category) {
            insertCatalogItemSubscribe(roomHelper.insertItemCategory((Category) catalog));
            return;
        }
        if (catalog instanceof Group) {
            insertCatalogItemSubscribe(roomHelper.insertItemGroup((Group) catalog));
            return;
        }
        if (catalog instanceof WorkForm) {
            insertCatalogItemSubscribe(roomHelper.insertItemWorkForm((WorkForm) catalog));
        }
    }

    @SuppressLint("CheckResult")
    private void insertCatalogItemSubscribe(Completable completable) {
        completable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            catalogList.add(catalog);
                            getViewState().updateRecyclerView();
                        },
                        er -> getViewState().showToast(ERROR_INSERTING_CATALOG_ITEM_TO_DATABASE + er.getMessage())
                ).isDisposed();
    }
}
