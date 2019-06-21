package ru.geekbrains.psy_journal.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.model.data.Catalog;
import ru.geekbrains.psy_journal.model.data.Category;
import ru.geekbrains.psy_journal.model.data.Group;
import ru.geekbrains.psy_journal.model.data.WorkForm;
import ru.geekbrains.psy_journal.model.database.RoomHelper;
import ru.geekbrains.psy_journal.model.factory.CatalogFactory;
import ru.geekbrains.psy_journal.view.dialogs.EditableDialogView;
import ru.geekbrains.psy_journal.view.fragment.IViewHolderCatalog;

@InjectViewState
public class EditableDialogPresenter extends MvpPresenter<EditableDialogView> {

    @Inject
    CatalogFactory catalogFactory;
    @Inject
    RoomHelper roomHelper;
    private List<Catalog> catalogList;
    private String title;

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
                }, throwable -> getViewState().showToast("Error loading data from database" + throwable.getMessage()));
    }

    @SuppressLint("CheckResult")
    public void getGroup() {
        roomHelper.getListGroups()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    catalogList.addAll(list);
                    getViewState().updateRecyclerView();
                }, throwable -> getViewState().showToast("Error loading data from database" + throwable.getMessage()));
    }

    @SuppressLint("CheckResult")
    public void getWorkForm() {
        roomHelper.getListWorkForms()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    catalogList.addAll(list);
                    getViewState().updateRecyclerView();
                }, throwable -> getViewState().showToast("Error loading data from database" + throwable.getMessage()));
    }

    // TODO Метод для выбора элемента
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

    // TODO Метод для добавления нового элемента каталога
    public void insertCatalogItem(String name) {
        Catalog catalog = catalogFactory.getCatalog(title);
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
    private void insertCatalogItemSubscribe(Single<Long> id) {
        id.
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(item -> getViewState().updateRecyclerView(),
                        throwable -> getViewState().showToast("Error inserting catalog item to database" + throwable.getMessage()));
    }
}
