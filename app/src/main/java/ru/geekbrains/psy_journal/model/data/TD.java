package ru.geekbrains.psy_journal.model.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import ru.geekbrains.psy_journal.model.data.converters.TFConverter;

import static ru.geekbrains.psy_journal.Constants.TABLE_TD;

@Entity(tableName = TABLE_TD)
public class TD {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String code;
    private String name;

    @TypeConverters(TFConverter.class)
    private TF tf;

    public TD(int id, String code, String name) {
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

    public TF getTf() {
        return tf;
    }

    public void setTf(TF tf) {
        this.tf = tf;
    }
}
