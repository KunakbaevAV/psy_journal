package ru.geekbrains.psy_journal.presentation.presenter.view_ui;

import com.arellomobile.mvp.MvpView;

public interface ShownMessage extends MvpView {
	void showMessage(String message);
}
