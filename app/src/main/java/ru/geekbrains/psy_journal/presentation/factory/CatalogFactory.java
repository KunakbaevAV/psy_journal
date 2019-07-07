package ru.geekbrains.psy_journal.presentation.factory;

import android.content.Context;

import javax.inject.Inject;

import ru.geekbrains.psy_journal.R;
import ru.geekbrains.psy_journal.di.App;
import ru.geekbrains.psy_journal.data.repositories.model.Catalog;
import ru.geekbrains.psy_journal.data.repositories.model.Category;
import ru.geekbrains.psy_journal.data.repositories.model.Group;
import ru.geekbrains.psy_journal.data.repositories.model.WorkForm;

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
