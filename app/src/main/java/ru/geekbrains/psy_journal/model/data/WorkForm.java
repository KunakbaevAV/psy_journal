package ru.geekbrains.psy_journal.model.data;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static ru.geekbrains.psy_journal.Constants.TABLE_WORK_FORM;

@Entity(tableName = TABLE_WORK_FORM)
public class WorkForm {

	@PrimaryKey(autoGenerate = true)
	private int id;
	private String name;

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
