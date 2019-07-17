package ru.geekbrains.psy_journal.presentation.presenter.view_ui.fragments;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.geekbrains.psy_journal.data.repositories.model.Catalog;

public interface ChangableView extends ReportingView {
	@StateStrategyType(AddToEndSingleStrategy.class)
	void changeName(Catalog catalog, int position);
	@StateStrategyType(AddToEndSingleStrategy.class)
	void remove(Catalog catalog);
}
