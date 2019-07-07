package ru.geekbrains.psy_journal.presentation.view.fragment.adapters;

import ru.geekbrains.psy_journal.presentation.presenter.Derivable;

public interface ReportRelated extends Derivable {
    void bindView(ReportShown reportShown, int position);
}
