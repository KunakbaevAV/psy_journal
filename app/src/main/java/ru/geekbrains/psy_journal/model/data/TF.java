package ru.geekbrains.psy_journal.model.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static ru.geekbrains.psy_journal.Constants.TABLE_TF;

@Entity(tableName = TABLE_TF)
public class TF {

	@PrimaryKey(autoGenerate = true)
	private int id;
	private String code;
	private String name;
	private OTF otf;
}
