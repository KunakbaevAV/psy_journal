package ru.geekbrains.psy_journal.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ru.geekbrains.psy_journal.Constants;
import ru.geekbrains.psy_journal.model.data.Category;
import ru.geekbrains.psy_journal.model.data.Functional;
import ru.geekbrains.psy_journal.model.data.Group;
import ru.geekbrains.psy_journal.model.data.Journal;
import ru.geekbrains.psy_journal.model.data.WorkForm;
import ru.geekbrains.psy_journal.model.database.RoomHelper;
import ru.geekbrains.psy_journal.view.fragment.AddWorkView;

@InjectViewState
public class AddWorkPresenter extends MvpPresenter<AddWorkView> implements
	Settable,
	SettableByDate,
	SettableByTime,
	SettableByFunction{

	private static final float HOUR_IN_MINUTES = 60.0f;
	private Journal journal;
	private boolean isRepeated;
	private Disposable disposable;

    @Inject  RoomHelper roomHelper;

	public Journal getJournal() {
		return journal;
	}

	public void initialize(Journal journal){
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
		getCroup(journal.getIdGroup());
		getViewState().showName(journal.getName());
		getViewState().showDeclaredRequest(journal.getDeclaredRequest());
		getViewState().showRealRequest(journal.getRealRequest());
		getWorkForm(journal.getIdWorkForm());
		getViewState().showTd(journal.getCodeTd());
		getViewState().showComment(journal.getComment());
	}

	private void getWorkForm(int id){
		if (id == 0) return;
		disposable = roomHelper.getItemWorkForm(id)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(workForm -> getViewState().showWorkForm(workForm.getName()),
				e -> Log.e("getWorkForm: ", e.getMessage()));
	}

	private void getCroup(int id){
		if (id == 0) return;
		disposable = roomHelper.getItemGroup(id)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(group -> getViewState().showGroup(group.getName()),
				e -> Log.e("getCroup: ", e.getMessage()));
	}

	private void getCategory(int id){
		if (id == 0) return;
		disposable = roomHelper.getItemCategory(id)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(category -> getViewState().showCategory(category.getName()),
				e -> Log.e("getCategory: ", e.getMessage()));
	}

	public void addWorkIntoDatabase(){
		if (isRepeated)	roomHelper.updateItemJournal(journal)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe();
		else roomHelper.insertItemJournal(journal)
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe();
	}

    public void setGroup(Group group) {
        journal.setIdGroup(group.getId());
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
		String code = function.getCode();
		if (code.equals(Constants.CODE_OF_OTHER_ACTIVITY)){
			code = function.getName();
		}
		if (close){
			journal.setCodeTd(code);
			getViewState().closeDialogs();
			getViewState().showTd(code);
		} else getViewState().openDialogue(function);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (disposable != null) disposable.dispose();
	}
}