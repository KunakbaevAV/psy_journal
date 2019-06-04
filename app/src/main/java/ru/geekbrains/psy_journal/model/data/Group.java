package ru.geekbrains.psy_journal.model.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static ru.geekbrains.psy_journal.Constants.TABLE_GROUP;

@Entity(tableName = TABLE_GROUP)
public class Group {

	@PrimaryKey(autoGenerate = true)
	private int id;
	private String name;
}
