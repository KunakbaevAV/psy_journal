package ru.geekbrains.psy_journal.model.data;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static ru.geekbrains.psy_journal.Constants.TABLE_WORK_FORM;

/**
 * Формы, в которых проводилась работа пользователя
 */
@Entity(tableName = TABLE_WORK_FORM)
public class WorkForm implements Catalog {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;

    public WorkForm() {
    }

    @Ignore
    public WorkForm(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
