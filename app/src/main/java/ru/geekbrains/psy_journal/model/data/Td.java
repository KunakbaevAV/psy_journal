package ru.geekbrains.psy_journal.model.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static ru.geekbrains.psy_journal.Constants.TABLE_TD;

@Entity(tableName = TABLE_TD)
public class Td {

	@PrimaryKey(autoGenerate = true)
	private int id;
	private String code;
	private String name;
	private Tf tf;
}
