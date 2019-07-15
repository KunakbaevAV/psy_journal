package ru.geekbrains.psy_journal.presentation.presenter;

import ru.geekbrains.psy_journal.data.repositories.model.Category;
import ru.geekbrains.psy_journal.data.repositories.model.Group;
import ru.geekbrains.psy_journal.data.repositories.model.WorkForm;

public interface SettableByCatalog {
    void saveSelectedCategory(Category category);

    void saveSelectedGroup(Group group);

    void saveSelectedWorkForm(WorkForm workForm);

}
