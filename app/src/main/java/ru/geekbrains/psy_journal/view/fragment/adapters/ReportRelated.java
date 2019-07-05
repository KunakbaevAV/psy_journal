package ru.geekbrains.psy_journal.view.fragment.adapters;

import ru.geekbrains.psy_journal.presenter.Derivable;

public interface ReportRelated extends Derivable {
    void bindView(ReportShown reportShown, int position);
}
