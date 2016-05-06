package ru.zhukov.domain;

/**
 * Created by Gukov on 06.05.2016.
 */
public class AccrualEmployee {


    private static final String CODE_BANK_OFFICE = "8596";
    private String persId;
    private String tabel;
    private String department;
    private String nameEmployee;

    private String accountByBank;
    private String receiverName;
    private String receiverFirstName;
    private String receiverSecondtName;
    private String accountNumber;

    private String code;
    private String codeName;
    private Double summa;


    private String office;
    private String branchOffice;

    private String fullNameReceiver;

    public String getFullNameReceiver() {
        return String.format("%s %s %s",receiverName.trim(),receiverFirstName.trim(),receiverSecondtName.trim());
    }

    public String getDepartment() {
        return department;
    }

    public String getPersId() {
        return persId;
    }

    public void setPersId(String persId) {
        this.persId = persId;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getNameEmployee() {
        return nameEmployee;
    }

    public void setNameEmployee(String nameEmployee) {
        this.nameEmployee = nameEmployee;
    }

    public String getAccountByBank() {
        return accountByBank.trim();
    }

    public void setAccountByBank(String accountByBank) {
        this.accountByBank = accountByBank;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverFirstName() {
        return receiverFirstName;
    }

    public void setReceiverFirstName(String receiverFirstName) {
        this.receiverFirstName = receiverFirstName;
    }

    public String getReceiverSecondtName() {
        return receiverSecondtName;
    }

    public void setReceiverSecondtName(String receiverSecondtName) {
        this.receiverSecondtName = receiverSecondtName;
    }

    public String getAccountNumber() {
        return accountNumber.trim();
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public Double getSumma() {
        return summa;
    }

    public void setSumma(Double summa) {
        this.summa = summa;
    }

    public String getTabel() {
        return tabel;
    }

    public void setTabel(String tabel) {
        this.tabel = tabel;
    }

    public String getBranchOffice() {
        return CODE_BANK_OFFICE;
    }

    public String getOffice() {
        return CODE_BANK_OFFICE;
    }
}
