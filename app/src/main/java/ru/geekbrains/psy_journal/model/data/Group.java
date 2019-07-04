package ru.geekbrains.psy_journal.model.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static ru.geekbrains.psy_journal.Constants.TABLE_GROUP;

/**
 * Группа, с которой проводится работа
 */
@Entity(tableName = TABLE_GROUP)
public class Group implements Catalog {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;

    @Ignore
    public Group(String name) {
        this.name = name;
    }

    @Ignore
    public Group(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Group() {
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
