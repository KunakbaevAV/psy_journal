package ru.geekbrains.psy_journal.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.Calendar;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.psy_journal.model.data.Journal;
import ru.geekbrains.psy_journal.model.database.RoomHelper;
import ru.geekbrains.psy_journal.view.fragment.Added;
@InjectViewState
public class AddWorkPresenter extends MvpPresenter<Added> implements Settable {

	private Journal journal;
	//инжектировать класс для отправки Journal в БД

    @Inject
    RoomHelper roomHelper;

	public Journal getJournal() {
		return journal;
	}

	public AddWorkPresenter(Journal journal) {
		this.journal = journal;
	}

    @SuppressLint("CheckResult")
    public void addWorkIntoDatabase() {
        journal = getJournalItem();
        addWorkObservable(journal)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(work -> getViewState().showToast("Work was added"),
                        throwable -> getViewState().showToast("Error adding work to database " + throwable.getMessage()));
    }

    private Single<Long> addWorkObservable(Journal journal) {
        return Single.create((SingleOnSubscribe<Long>) emitter -> {
            long id = roomHelper.insertItemJournal(journal);
            emitter.onSuccess(id);
        }).subscribeOn(Schedulers.io());
    }

    private Journal getJournalItem() { //FIXME Тестовый метод создания единицы работы для проверки добавления в БД
        long date = 38100;
        String dayOfWeek = "Sunday";
        int td = 3;
        int category = 2;
        int group = 3;
        String name = "Ivanov";
        int quantityPeople = 1;
        String declaredRequest = "Theme - Declared request";
        String realRequest = "Real";
        int workForm = 1;
        float workTime = (float) 0.5;
        String comment = "Comment";
        journal = new Journal(date, dayOfWeek, td, category,
                group, name, quantityPeople, declaredRequest, realRequest,
                workForm, workTime, comment);

        return journal;
    }

    public void setNameGroup(String name) { //FIXME Переделать, т.к. изменились поля класса Journal
        //journal.setIdGroup(new Group(name));
        int idGroup = 1;
        journal.setIdGroup(idGroup);
	}

	@Override
	public void setDate(int year, int month, int dayOfMonth) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, dayOfMonth);
		long date = calendar.getTimeInMillis();
		journal.setDate(date);
		getViewState().showDate(date);
	}

	@Override
	public void setHours(int hour, int minutes) {
		float hours = hour + minutes / 60.0f;
		journal.setWorkTime(hours);
		getViewState().showHours(hours);
	}
}
