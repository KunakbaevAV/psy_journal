package ru.geekbrains.psy_journal.presentation.presenter.view_ui;

import ru.geekbrains.psy_journal.presentation.presenter.Derivable;

public interface ReportRelated extends Derivable {
    void bindView(ReportShown reportShown, int position);
}
