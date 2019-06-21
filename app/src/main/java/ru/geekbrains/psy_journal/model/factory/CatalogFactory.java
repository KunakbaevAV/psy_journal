package ru.geekbrains.psy_journal.model.factory;

import ru.geekbrains.psy_journal.model.data.Catalog;
import ru.geekbrains.psy_journal.model.data.Category;
import ru.geekbrains.psy_journal.model.data.Group;
import ru.geekbrains.psy_journal.model.data.WorkForm;

public class CatalogFactory {

    public Catalog getCatalog(String title) { //FIXME Заменить строки ресурсами
        switch (title) {
            case "Категория": {
                return new Category();
            }
            case "Класс/группа": {
                return new Group();
            }
            case "Форма работы": {
                return new WorkForm();
            }
        }
        return null;
    }
}
