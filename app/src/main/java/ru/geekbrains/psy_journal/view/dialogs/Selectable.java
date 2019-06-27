package ru.geekbrains.psy_journal.view.dialogs;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.geekbrains.psy_journal.model.data.OTF;

public interface Selectable extends MvpView {
	@StateStrategyType(SingleStateStrategy.class)
	void fillAdapter(List<OTF> list);
	@StateStrategyType(AddToEndSingleStrategy.class)
	void showSelectedFrom(long date);
	@StateStrategyType(AddToEndSingleStrategy.class)
	void showSelectedUnto(long date);
	@StateStrategyType(AddToEndSingleStrategy.class)
	void transferData(int idOTF, long from, long unto);
}
