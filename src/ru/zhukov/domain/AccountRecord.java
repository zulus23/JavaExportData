package ru.zhukov.domain;

/**
 * Created by Gukov on 28.03.2016.
 * Проводка в АиТ
 */
public class AccountRecord {

    private String department;
    private String employee;
    private String debit;
    private String credit;
    private Double summa;
    private String costItem;
    private String objectId;
    private String taxArticle;
    private String cfo;

    private int  year;
    private int month;
    private String description;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getDebit() {
        return debit;
    }

    public void setDebit(String debit) {
        this.debit = debit;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public Double getSumma() {
        return summa;
    }

    public void setSumma(Double summa) {
        this.summa = summa;
    }

    public String getCostItem() {
        return costItem;
    }

    public void setCostItem(String costItem) {
        this.costItem = costItem;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getTaxArticle() {
        return taxArticle;
    }

    public void setTaxArticle(String taxArticle) {
        this.taxArticle = taxArticle;
    }

    public String getCfo() {
        return cfo;
    }

    public void setCfo(String cfo) {
        this.cfo = cfo;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "AccountRecord{" +
                "employee='" + employee + '\'' +
                ", debit='" + debit + '\'' +
                ", credit='" + credit + '\'' +
                ", summa=" + summa +
                ", year=" + year +
                ", month=" + month +
                ", description='" + description + '\'' +
                '}';
    }
}
