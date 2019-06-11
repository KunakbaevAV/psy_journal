package ru.geekbrains.psy_journal.model.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.geekbrains.psy_journal.model.data.converters.JournalConverter;

import static ru.geekbrains.psy_journal.Constants.TABLE_JOURNAL;

@Entity(tableName = TABLE_JOURNAL)
@TypeConverters(JournalConverter.class)
public class Journal {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private long date = new Date().getTime();
    @Ignore
    private String dayOfWeek = determineDayOfWeek(date);
    private int idTd;
    private int idCategory;
    private int idGroup;
    private String name;
    private int quantityPeople;
    private String declaredRequest;
    private String realRequest;
    private int idWorkForm;
    private Float workTime = 1.0f;
    private String comment;

    public Journal() {

    }

    @Ignore
    public Journal(long date, String dayOfWeek, int idTd,
                   int idCategory, int idGroup, String name,
                   int quantityPeople, String declaredRequest,
                   String realRequest, int idWorkForm,
                   Float workTime, String comment) {
        this.date = date;
        this.dayOfWeek = dayOfWeek;
        this.idTd = idTd;
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
        dayOfWeek = determineDayOfWeek(date);
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public int getIdTd() {
        return idTd;
    }

    public void setIdTd(int idTd) {
        this.idTd = idTd;
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

    private String determineDayOfWeek(long date){
    	if (date != 0){
    		return new SimpleDateFormat("EEEE", Locale.getDefault()).format(new Date(date));
	    }
    	return null;
    }
}
