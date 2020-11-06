package ru.geekbrains.psy_journal.data.repositories;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.geekbrains.psy_journal.data.repositories.model.Category;

public interface StorableCategory {

	Single<List<Category>> getListCategory();
	Completable deleteItemCategory(Category category);
	Completable updateItemCategory(Category category);
	Single<Category> getItemCategory(int id);
	Single<Category> getAddedCategoryItem(String name);
}
