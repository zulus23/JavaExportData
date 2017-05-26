package ru.zhukov.domain;



import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Gukov on 19.04.2017.
 */


@NamedStoredProcedureQuery(name = "createAccountsForTransfer",procedureName =  "Gtk_Create_TransferTo1C",
  resultClasses = TransferAccount.class,

  parameters = {
   @StoredProcedureParameter( name = "v_year",type = Integer.class),
   @StoredProcedureParameter( name = "v_month",type = Integer.class)
})
@Entity
public class TransferAccount implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer numberOrder;
    private String accountDebit;
    private String departmentDebit;
    private String analyticsOneDebit;
    private String analyticsTwoDebit;
    private String analyticsThreeDebit;
    private String accountCredit;
    private String departmentCredit;
    private String analyticsOneCredit;
    private String analyticsTwoCredit;
    private String analyticsThreeCredit;
    private Double summaAccount;
    private Double  summaTaxDebit;
    private Double summaTaxCredit;
    private Double summaPRDebit;

    public Integer getNumberOrder() {
        return numberOrder;
    }

    public void setNumberOrder(Integer numberOrder) {
        this.numberOrder = numberOrder;
    }

    public String getAccountDebit() {
        return accountDebit;
    }

    public void setAccountDebit(String accountDebit) {
        this.accountDebit = accountDebit;
    }

    public String getDepartmentDebit() {
        return departmentDebit;
    }

    public void setDepartmentDebit(String departmentDebit) {
        this.departmentDebit = departmentDebit;
    }

    public String getAnalyticsOneDebit() {
        return analyticsOneDebit;
    }

    public void setAnalyticsOneDebit(String analyticsOneDebit) {
        this.analyticsOneDebit = analyticsOneDebit;
    }

    public String getAnalyticsTwoDebit() {
        return analyticsTwoDebit;
    }

    public void setAnalyticsTwoDebit(String analyticsTwoDebit) {
        this.analyticsTwoDebit = analyticsTwoDebit;
    }

    public String getAnalyticsThreeDebit() {
        return analyticsThreeDebit;
    }

    public void setAnalyticsThreeDebit(String analyticsThreeDebit) {
        this.analyticsThreeDebit = analyticsThreeDebit;
    }

    public String getAccountCredit() {
        return accountCredit;
    }

    public void setAccountCredit(String accountCredit) {
        this.accountCredit = accountCredit;
    }

    public String getDepartmentCredit() {
        return departmentCredit;
    }

    public void setDepartmentCredit(String departmentCredit) {
        this.departmentCredit = departmentCredit;
    }

    public String getAnalyticsOneCredit() {
        return analyticsOneCredit;
    }

    public void setAnalyticsOneCredit(String analyticsOneCredit) {
        this.analyticsOneCredit = analyticsOneCredit;
    }

    public String getAnalyticsTwoCredit() {
        return analyticsTwoCredit;
    }

    public void setAnalyticsTwoCredit(String analyticsTwoCredit) {
        this.analyticsTwoCredit = analyticsTwoCredit;
    }

    public String getAnalyticsThreeCredit() {
        return analyticsThreeCredit;
    }

    public void setAnalyticsThreeCredit(String analyticsThreeCredit) {
        this.analyticsThreeCredit = analyticsThreeCredit;
    }

    public Double getSummaAccount() {
        return summaAccount;
    }

    public void setSummaAccount(Double summaAccount) {
        this.summaAccount = summaAccount;
    }

    public Double getSummaTaxDebit() {
        return summaTaxDebit;
    }

    public void setSummaTaxDebit(Double summaTaxDebit) {
        this.summaTaxDebit = summaTaxDebit;
    }

    public Double getSummaTaxCredit() {
        return summaTaxCredit;
    }

    public void setSummaTaxCredit(Double summaTaxCredit) {
        this.summaTaxCredit = summaTaxCredit;
    }

    public Double getSummaPRDebit() {
        return summaPRDebit;
    }

    public void setSummaPRDebit(Double summaPRDebit) {
        this.summaPRDebit = summaPRDebit;
    }
}
