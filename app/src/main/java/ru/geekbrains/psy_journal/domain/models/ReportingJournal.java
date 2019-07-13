package ru.geekbrains.psy_journal.domain.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ReportingJournal {

	private static final String PATTERN_DAY_WEEK = "EEEE";

	private String date;
	private String dayOfWeek;
	private String quantityPeople;
	private String workTime;
	private String nameCategory;
	private String nameGroup;
	private String name;
	private String declaredRequest;
	private String realRequest;
	private String nameWorkForm;
	private String codeTd;
	private String comment;

	public String getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault()).format(new Date(date));
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(long date) {
		this.dayOfWeek = determineDayOfWeek(date);
	}

	public String getQuantityPeople() {
		return quantityPeople;
	}

	public void setQuantityPeople(int quantityPeople) {
		this.quantityPeople = String.valueOf(quantityPeople);
	}

	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(Float workTime) {
		this.workTime = String.valueOf(workTime);
	}

	public String getNameCategory() {
		return nameCategory;
	}

	public void setNameCategory(String nameCategory) {
		this.nameCategory = nameCategory;
	}

	public String getNameGroup() {
		return nameGroup;
	}

	public void setNameGroup(String nameGroup) {
		this.nameGroup = nameGroup;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getNameWorkForm() {
		return nameWorkForm;
	}

	public void setNameWorkForm(String nameWorkForm) {
		this.nameWorkForm = nameWorkForm;
	}

	public String getCodeTd() {
		return codeTd;
	}

	public void setCodeTd(String codeTd) {
		this.codeTd = codeTd;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	private String determineDayOfWeek(long date){
		if (date != 0){
			return new SimpleDateFormat(PATTERN_DAY_WEEK, Locale.getDefault()).format(new Date(date));
		}
		return null;
	}
}
