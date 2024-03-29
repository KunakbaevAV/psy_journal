package ru.geekbrains.psy_journal.presentation.presenter.fragments;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.data.repositories.StorableCategory;
import ru.geekbrains.psy_journal.data.repositories.StorableGroup;
import ru.geekbrains.psy_journal.data.repositories.StorableJournal;
import ru.geekbrains.psy_journal.data.repositories.StorableWorkForm;
import ru.geekbrains.psy_journal.data.repositories.model.Category;
import ru.geekbrains.psy_journal.data.repositories.model.Functional;
import ru.geekbrains.psy_journal.data.repositories.model.Group;
import ru.geekbrains.psy_journal.data.repositories.model.Journal;
import ru.geekbrains.psy_journal.data.repositories.model.WorkForm;
import ru.geekbrains.psy_journal.presentation.presenter.SettableByCatalog;
import ru.geekbrains.psy_journal.presentation.presenter.SettableByDate;
import ru.geekbrains.psy_journal.presentation.presenter.SettableByFunction;
import ru.geekbrains.psy_journal.presentation.presenter.SettableByTime;
import ru.geekbrains.psy_journal.presentation.presenter.view_ui.fragments.AddWorkView;

@InjectViewState
public class AddWorkPresenter extends MvpPresenter<AddWorkView> implements
	SettableByDate,
	SettableByTime,
	SettableByFunction,
	SettableByCatalog {

	@Inject StorableJournal storableJournal;
	@Inject StorableWorkForm storableWorkForm;
	@Inject StorableGroup storableGroup;
	@Inject StorableCategory storableCategory;

	private static final float HOUR_IN_MINUTES = 60.0f;
	private Journal journal;
	private boolean isRepeated;
	private Disposable disposable;

	public Journal getJournal() {
		return journal;
	}

    private void getNames() {
        disposable = storableJournal.getListFullNames()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> getViewState().getNames(list),
                        e -> Log.e("getNames: e", e.getMessage()));
    }

	public void initialize(Journal journal){
        getNames();
		if (journal == null) this.journal = new Journal();
		else {
			this.journal = journal;
			isRepeated = true;
			init();
		}
	}

	private void init(){
		getViewState().showDate(journal.getDate());
		getViewState().showNumberOfPeople(journal.getQuantityPeople());
		getViewState().showHours(journal.getWorkTime());
		getCategory(journal.getIdCategory());
		getGroup(journal.getIdGroup());
		getViewState().showName(journal.getName());
		getViewState().showDeclaredRequest(journal.getDeclaredRequest());
		getViewState().showRealRequest(journal.getRealRequest());
		getWorkForm(journal.getIdWorkForm());
		getViewState().showTd(journal.getCodeTd());
		getViewState().showComment(journal.getComment());
	}

	private void getWorkForm(int id){
		if (id == 0) return;
		disposable = storableWorkForm.getItemWorkForm(id)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(workForm -> getViewState().showWorkForm(workForm.getName()),
				e -> Log.e("getWorkForm: e", e.getMessage()));
	}

	private void getGroup(int id) {
		if (id == 0) return;
		disposable = storableGroup.getItemGroup(id)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(group -> getViewState().showGroup(group.getName()),
					e -> Log.e("getGroup: e", e.getMessage()));
	}

	private void getCategory(int id){
		if (id == 0) return;
		disposable = storableCategory.getItemCategory(id)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(category -> getViewState().showCategory(category.getName()),
				e -> Log.e("getCategory: e", e.getMessage()));
	}

	public void addWorkIntoDatabase(){
		if (isRepeated)	storableJournal.updateItemJournal(journal)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe();
		else storableJournal.insertItemJournal(journal)
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe();
	}

	@Override
    public void saveSelectedCategory(Category category) {
		journal.setIdCategory(category.getId());
		getViewState().showCategory(category.getName());
	}

	@Override
    public void saveSelectedGroup(Group group) {
		journal.setIdGroup(group.getId());
		getViewState().showGroup(group.getName());
	}

	@Override
    public void saveSelectedWorkForm(WorkForm workForm) {
		journal.setIdWorkForm(workForm.getId());
		getViewState().showWorkForm(workForm.getName());
	}

	@Override
	public void setDate(long date) {
		journal.setDate(date);
		getViewState().showDate(date);
	}

	@Override
	public void setHours(int hour, int minutes) {
		float hours = hour + minutes / HOUR_IN_MINUTES;
		journal.setWorkTime(hours);
		getViewState().showHours(hours);
	}

	@Override
	public void setFunction(Functional function, boolean close) {
        String name = function.getCode();
        if (name.endsWith(Constants.CODE_OF_OTHER_ACTIVITY_SUFFIX)) {
            name = function.getName();
        }
		if (close){
            journal.setCodeTd(function.getCode());
			getViewState().closeDialogs();
            getViewState().showTd(name);
		} else getViewState().openDialogue(function);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (disposable != null) disposable.dispose();
	}
}