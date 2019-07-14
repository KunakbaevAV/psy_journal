package ru.geekbrains.psy_journal.data.repositories.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import static ru.geekbrains.psy_journal.Constants.TABLE_JOURNAL;

/**
 * Журнал регистрации единиц работы пользователя
 */
@Entity(tableName = TABLE_JOURNAL)
public class Journal implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private long date = new Date().getTime();
    private int quantityPeople;
    private Float workTime = 1.0f;
    private int idCategory;
    private int idGroup;
    private String name;
    private String declaredRequest;
    private String realRequest;
    private int idWorkForm;
    private String codeTd;
    private String comment;

    public Journal() {
    }

    public static final Creator<Journal> CREATOR = new Creator<Journal>() {
        @Override
        public Journal createFromParcel(Parcel in) {
            return new Journal(in);
        }

        @Override
        public Journal[] newArray(int size) {
            return new Journal[size];
        }
    };

    protected Journal(Parcel in) {
        setId(in.readInt());
        setDate(in.readLong());
        setCodeTd(in.readString());
        setIdCategory(in.readInt());
        setIdGroup(in.readInt());
        setName(in.readString());
        setQuantityPeople(in.readInt());
        setDeclaredRequest(in.readString());
        setRealRequest(in.readString());
        setIdWorkForm(in.readInt());
        if (in.readByte() == 0) {
            setWorkTime(null);
        } else {
            setWorkTime(in.readFloat());
        }
        setComment(in.readString());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getCodeTd() {
        return codeTd;
    }

    public void setCodeTd(String codeTd) {
        this.codeTd = codeTd;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantityPeople() {
        return quantityPeople;
    }

    public void setQuantityPeople(int quantityPeople) {
        this.quantityPeople = quantityPeople;
    }

    public String getDeclaredRequest() {
        return declaredRequest;
    }

    public void setDeclaredRequest(String declaredRequest) {
        this.declaredRequest = declaredRequest;
    }

    public String getRealRequest() {
        return realRequest;
    }

    public void setRealRequest(String realRequest) {
        this.realRequest = realRequest;
    }

    public int getIdWorkForm() {
        return idWorkForm;
    }

    public void setIdWorkForm(int idWorkForm) {
        this.idWorkForm = idWorkForm;
    }

    public Float getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Float workTime) {
        this.workTime = workTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeLong(getDate());
        dest.writeString(getCodeTd());
        dest.writeInt(getIdCategory());
        dest.writeInt(getIdGroup());
        dest.writeString(getName());
        dest.writeInt(getQuantityPeople());
        dest.writeString(getDeclaredRequest());
        dest.writeString(getRealRequest());
        dest.writeInt(getIdWorkForm());
        if (getWorkTime() == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(getWorkTime());
        }
        dest.writeString(getComment());
    }
}
