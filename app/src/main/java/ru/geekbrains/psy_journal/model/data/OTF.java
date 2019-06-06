package ru.geekbrains.psy_journal.model.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static ru.geekbrains.psy_journal.Constants.TABLE_OTF;

@Entity(tableName = TABLE_OTF)
public class OTF {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String code;
    private String name;
}
