package ru.geekbrains.psy_journal.model.data;

public class ReportData {

    private String nameTF;
    private int quantityPeople;
    private float workTime;

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
