package ru.zhukov.domain;




import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "vari")
public class EmployeeFee {
    @Id
    @Column(name = "key_field")
    private String id;

    @Column(name = "prc")
    private BigDecimal procent;
    @Column(name = "days")
    private BigDecimal day;
    @Column(name = "hours")
    private BigDecimal hour;
    @Column(name = "mm")
    private Integer month;
    @Column(name = "yy")
    private Integer year;
    @Column(name = "fin_op")
    private String financeOperation;
    @Column(name = "tip")
    private String tip;
    @OneToOne
    @JoinColumn(name = "code")
    private KindPay kindPay;
    @Column(name = "brv")
    private String graphWork;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public BigDecimal getProcent() {
        return procent;
    }

    public void setProcent(BigDecimal procent) {
        this.procent = procent;
    }

    public BigDecimal getDay() {
        return day;
    }

    public void setDay(BigDecimal day) {
        this.day = day;
    }

    public BigDecimal getHour() {
        return hour;
    }

    public void setHour(BigDecimal hour) {
        this.hour = hour;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getFinanceOperation() {
        return financeOperation;
    }

    public void setFinanceOperation(String financeOperation) {
        this.financeOperation = financeOperation;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public KindPay getKindPay() {
        return kindPay;
    }

    public void setKindPay(KindPay kindPay) {
        this.kindPay = kindPay;
    }

    public String getGraphWork() {
        return graphWork;
    }

    public void setGraphWork(String graphWork) {
        this.graphWork = graphWork;
    }
}
