package ru.zhukov.domain;

import java.util.Date;

/**
 * Created by Gukov on 18.04.2016.
 */
public class JournalExportDetail {
    private long recNo;
    private int state;
    private String parentRecId; // поле свзяи с journalExportHeader
    private long tableRecNo;
    private Date transDate;
    private String debit;
    private String credit;
    private String currentCode;
    private Double summa;

    private String text;
    //private int  year;
    //private int month;

    private String transDimension;
    private String transDimension4; //Объект
    private String transDimension2; //Затраты
    private String transDimension7;//ЦФО
    private String transDimension8; //Налог



    public long getRecNo() {
        return recNo;
    }

    public void setRecNo(long recNo) {
        this.recNo = recNo;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getParentRecId() {
        return parentRecId;
    }

    public void setParentRecId(String parentRecId) {
        this.parentRecId = parentRecId;
    }

    public long getTableRecNo() {
        return tableRecNo;
    }

    public void setTableRecNo(long tableRecNo) {
        this.tableRecNo = tableRecNo;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
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

    public String getCurrentCode() {
        return currentCode;
    }

    public void setCurrentCode(String currentCode) {
        this.currentCode = currentCode;
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

}
