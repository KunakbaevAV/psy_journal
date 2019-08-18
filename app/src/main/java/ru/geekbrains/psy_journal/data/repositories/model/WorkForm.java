package ru.geekbrains.psy_journal.data.repositories.model;


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

    @Ignore
    public WorkForm(String name) {
	    this.name = name;
    }

    public WorkForm(int id, String name) {
    	this(name);
        this.id = id;
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
