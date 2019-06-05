package ru.geekbrains.psy_journal.model.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static ru.geekbrains.psy_journal.Constants.TABLE_JOURNAL;

@Entity(tableName = TABLE_JOURNAL)
public class Journal {

	@PrimaryKey(autoGenerate = true)
	private int id;
    private long date;
	private TD td;
	private Category category;
	private Group group;
	private String name;
	private int quantityPeople;
	private String declaredRequest;
	private String realRequest;
	private WorkForm workForm;
	private Double workTime;
	private String comment;
}
