package ru.geekbrains.psy_journal.presentation.presenter.view_ui.dialogs;

import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import java.io.File;

import ru.geekbrains.psy_journal.presentation.presenter.view_ui.fragments.ReportingView;

@StateStrategyType(SingleStateStrategy.class)
public interface OpenFileDialogView extends ReportingView {

	void showCurrentDirectory(String path);

    void startLoadXml(File file);
}
