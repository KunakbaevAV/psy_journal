package ru.geekbrains.psy_journal.model.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static ru.geekbrains.psy_journal.Constants.TABLE_TF;

@Entity(tableName = TABLE_TF)
public class TF implements Functional{

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String code;
    private String name;
    private int idOTF;

	public TF(int id, String code, String name, int idOTF) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.idOTF = idOTF;
    }

	@Override
	public String getLabel() {
		// это просто метка, чтобы не тратить время на приведения класса обьекта через instanceof
		return "TF";
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

    public int getIdOTF() {
        return idOTF;
    }

    public void setIdOTF(int idOTF) {
        this.idOTF = idOTF;
    }
}
