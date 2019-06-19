package ru.geekbrains.psy_journal.view.fragment;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.geekbrains.psy_journal.model.data.Functional;

public interface AddWorkView extends MvpView {

	@StateStrategyType(AddToEndSingleStrategy.class)
	void showDate(long date);
	@StateStrategyType(AddToEndSingleStrategy.class)
	void showNumberOfPeople(int number);
	@StateStrategyType(AddToEndSingleStrategy.class)
	void showHours(float hours);
	@StateStrategyType(AddToEndSingleStrategy.class)
	void showCategory(String nameCategory);
	@StateStrategyType(AddToEndSingleStrategy.class)
	void showGroup(String nameGroup);
	@StateStrategyType(AddToEndSingleStrategy.class)
	void showName(String name);
	@StateStrategyType(AddToEndSingleStrategy.class)
	void showDeclaredRequest(String declaredRequest);
	@StateStrategyType(AddToEndSingleStrategy.class)
	void showRealRequest(String realRequest);
	@StateStrategyType(AddToEndSingleStrategy.class)
	void showWorkForm(String nameWorkForm);
	@StateStrategyType(AddToEndSingleStrategy.class)
	void showTd(Functional functional);
	@StateStrategyType(AddToEndSingleStrategy.class)
	void showComment(String comment);

	void collectAll();
	@StateStrategyType(AddToEndSingleStrategy.class)
	void openDialogue(String title, int id);
	@StateStrategyType(SingleStateStrategy.class)
    void closeDialogs();
	void showToast(String message);
}
