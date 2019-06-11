package ru.geekbrains.psy_journal.model.data;

import androidx.room.Entity;
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
    private String dayOfWeek = determineDayOfWeek(date);
    private TD td;
    private Category category;
    private Group group;
    private String name;
    private int quantityPeople;
    private String declaredRequest;
    private String realRequest;
    private WorkForm workForm;
    private Float workTime = 1.0f;
    private String comment;

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

	/**
	 * поле dayOfWeek нужно только для отчета  файл.xlsx
	 * Имеет ли смысл этот метод, если поле dayOfWeek зависит от поля date?
	 * поле dayOfWeek даже в базе хранить не нужно. При установке поля date
	 * автоматически методом String determineDayOfWeek(long date)
	 * отформатируется день недели и установится в поле dayOfWeek.
	 * @param dayOfWeek
	 */
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public TD getTd() {
        return td;
    }

    public void setTd(TD td) {
        this.td = td;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
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

    public WorkForm getWorkForm() {
        return workForm;
    }

    public void setWorkForm(WorkForm workForm) {
        this.workForm = workForm;
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
