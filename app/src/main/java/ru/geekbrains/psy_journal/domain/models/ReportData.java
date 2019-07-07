package ru.geekbrains.psy_journal.domain.models;

/**
 * Отчет о проделанной работе пользователя.
 * Учитывает охват человек, с которыми проводилась работа
 * и затраченное время.
 */
public class ReportData {

    private String codeTF;
    private String nameTF;
    private int quantityPeople;
    private float workTime;

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
