package ru.zhukov.domain;

/**
 * Created by Gukov on 11.04.2016.
 */
public class AccountRecordExport {

    private String debit;
    private String credit;
    private Double summa;
    private String text;
    private int  year;
    private int month;
    private String transDimension;
    private String transDimension4; //Объект
    private String transDimension2; //Затраты
    private String transDimension7;//ЦФО
    private String transDimension8; //Налог


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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public String getTransDimension() {
        return transDimension;
    }

    public void setTransDimension(String transDimension) {
        this.transDimension = transDimension;
    }

    public String getTransDimension4() {
        return transDimension4;
    }

    public void setTransDimension4(String transDimension4) {
        this.transDimension4 = transDimension4;
    }

    public String getTransDimension2() {
        return transDimension2;
    }

    public void setTransDimension2(String transDimension2) {
        this.transDimension2 = transDimension2;
    }

    public String getTransDimension7() {
        return transDimension7;
    }

    public void setTransDimension7(String transDimension7) {
        this.transDimension7 = transDimension7;
    }

    public String getTransDimension8() {
        return transDimension8;
    }

    public void setTransDimension8(String transDimension8) {
        this.transDimension8 = transDimension8;
    }

    @Override
    public String toString() {
        return "AccountRecordExport{" +
                "debit='" + debit + '\'' +
                ", credit='" + credit + '\'' +
                ", summa=" + summa +
                ", text='" + text + '\'' +
                ", year=" + year +
                ", month=" + month +
                ", transDimension='" + transDimension + '\'' +
                ", transDimension4='" + transDimension4 + '\'' +
                ", transDimension2='" + transDimension2 + '\'' +
                ", transDimension7='" + transDimension7 + '\'' +
                ", transDimension8='" + transDimension8 + '\'' +
                '}';
    }
}
