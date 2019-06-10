package ru.geekbrains.psy_journal.model.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import ru.geekbrains.psy_journal.model.data.converters.OTFConverter;

import static ru.geekbrains.psy_journal.Constants.TABLE_TF;

@Entity(tableName = TABLE_TF)
public class TF {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String code;
    private String name;

    @TypeConverters(OTFConverter.class)
    private OTF otf;

    public TF(int id, String code, String name, OTF otf) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.otf = otf;
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

    public OTF getOtf() {
        return otf;
    }

    public void setOtf(OTF otf) {
        this.otf = otf;
    }
}
