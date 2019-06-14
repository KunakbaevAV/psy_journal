package ru.geekbrains.psy_journal.model.data;

import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static ru.geekbrains.psy_journal.Constants.TABLE_TD;

@Entity(tableName = TABLE_TD)
public class TD implements Functional {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String code;
    private String name;
    private int idTF;

    public TD(int id, String code, String name, int idTF) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.idTF = idTF;
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

    public int getIdTF() {
        return idTF;
    }

    public void setIdTF(int idTF) {
        this.idTF = idTF;
    }
}
