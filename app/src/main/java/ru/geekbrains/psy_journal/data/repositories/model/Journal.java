package ru.geekbrains.psy_journal.data.repositories.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
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
    @ColumnInfo(index = true)
    private String name;
    @ColumnInfo(index = true)
    private String declaredRequest;
    private String realRequest;
    private int idWorkForm;
    @ColumnInfo(index = true)
    private String codeTd;
    private String comment;

    public Journal() {

    }

    @Ignore
    public Journal(long date, String codeTd,
                   int idCategory, int idGroup, String name,
                   int quantityPeople, String declaredRequest,
                   String realRequest, int idWorkForm,
                   Float workTime, String comment) {
        this.date = date;
        this.codeTd = codeTd;
        this.idCategory = idCategory;
        this.idGroup = idGroup;
        this.name = name;
        this.quantityPeople = quantityPeople;
        this.declaredRequest = declaredRequest;
        this.realRequest = realRequest;
        this.idWorkForm = idWorkForm;
        this.workTime = workTime;
        this.comment = comment;
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
        id = in.readInt();
        date = in.readLong();
        codeTd = in.readString();
        idCategory = in.readInt();
        idGroup = in.readInt();
        name = in.readString();
        quantityPeople = in.readInt();
        declaredRequest = in.readString();
        realRequest = in.readString();
        idWorkForm = in.readInt();
        if (in.readByte() == 0) {
            workTime = null;
        } else {
            workTime = in.readFloat();
        }
        comment = in.readString();
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
        dest.writeInt(id);
        dest.writeLong(date);
        dest.writeString(codeTd);
        dest.writeInt(idCategory);
        dest.writeInt(idGroup);
        dest.writeString(name);
        dest.writeInt(quantityPeople);
        dest.writeString(declaredRequest);
        dest.writeString(realRequest);
        dest.writeInt(idWorkForm);
        if (workTime == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(workTime);
        }
        dest.writeString(comment);
    }
}
