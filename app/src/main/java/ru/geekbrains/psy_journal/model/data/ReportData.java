package ru.geekbrains.psy_journal.model.data;

import androidx.room.Ignore;

public class ReportData {

    private String codeTF;
    private String nameTF;
    private int quantityPeople;
    private float workTime;

    public ReportData() {
    }

    @Ignore
    public ReportData(String codeTF, String nameTF, int quantityPeople, float workTime) {
        this.codeTF = codeTF;
        this.nameTF = nameTF;
        this.quantityPeople = quantityPeople;
        this.workTime = workTime;
    }

    public String getCodeTF() {
        return codeTF;
    }

    public void setCodeTF(String codeTF) {
        this.codeTF = codeTF;
    }

    public String getNameTF() {
        return nameTF;
    }

    public void setNameTF(String nameTF) {
        this.nameTF = nameTF;
    }

    public int getQuantityPeople() {
        return quantityPeople;
    }

    public void setQuantityPeople(int quantityPeople) {
        this.quantityPeople = quantityPeople;
    }

    public float getWorkTime() {
        return workTime;
    }

    public void setWorkTime(float workTime) {
        this.workTime = workTime;
    }

}
