package ru.geekbrains.psy_journal.data.repositories;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.geekbrains.psy_journal.data.repositories.model.WorkForm;

public interface StorableWorkForm {

	Single<List<WorkForm>> getListWorkForms();
	Completable deleteItemWorkForm(WorkForm workForm);
	Completable updateItemWorkForm(WorkForm workForm);
	Single<WorkForm> getItemWorkForm(int id);
	Single<WorkForm> getAddedWorkFormItem(String name);
}
