package ru.geekbrains.psy_journal.data.repositories.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static ru.geekbrains.psy_journal.Constants.TABLE_CATEGORY;

/**
 * Категория людей, с которыми проводится работа
 */
@Entity(tableName = TABLE_CATEGORY)
public class Category implements Catalog {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;

	@Ignore
    public Category(String name) {
		this.name = name;
    }

    public Category(int id, String name) {
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
