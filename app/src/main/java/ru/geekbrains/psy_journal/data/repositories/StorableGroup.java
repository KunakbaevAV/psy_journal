package ru.geekbrains.psy_journal.data.repositories;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.geekbrains.psy_journal.data.repositories.model.Group;

public interface StorableGroup {

	Single<List<Group>> getListGroups();
	Completable deleteItemGroup(Group group);
	Completable updateItemGroup(Group group);
	Single<Group> getItemGroup(int id);
	Single<Group> getAddedGroupItem(String name);
}
