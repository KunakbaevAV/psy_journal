package ru.geekbrains.psy_journal.model.factory;

import android.content.Context;

import javax.inject.Inject;

import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.model.data.Catalog;
import ru.geekbrains.psy_journal.model.data.Category;
import ru.geekbrains.psy_journal.model.data.Group;
import ru.geekbrains.psy_journal.model.data.WorkForm;

import static ru.geekbrains.psy_journal.R.string.choose_category;

public class CatalogFactory {

    @Inject
    Context context;

    public CatalogFactory() {
        App.getAppComponent().inject(this);
    }

    public Catalog getCatalog(String title) {
        if (title.equals(context.getString(choose_category))) return new Category();
        if (title.equals(context.getString(R.string.choose_group))) return new Group();
        if (title.equals(context.getString(R.string.choose_work_form))) return new WorkForm();

        return null;

    }
}
