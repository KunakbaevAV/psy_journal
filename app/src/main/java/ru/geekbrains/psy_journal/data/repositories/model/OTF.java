package ru.geekbrains.psy_journal.data.repositories.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static ru.geekbrains.psy_journal.Constants.TABLE_OTF;

/**
 * Обобщенная трудовая функция - термин из профстандарта
 */
@Entity(tableName = TABLE_OTF)
public class OTF implements Functional {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String code;
    private String name;

	public OTF(int id) {
		this.id = id;
	}

	public OTF(int id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
