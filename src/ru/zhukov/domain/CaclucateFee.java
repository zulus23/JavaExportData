package ru.zhukov.domain;




import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ras")
public class CaclucateFee {
    @Id
    @Column(name = "key_field")
    private String id;

    @OneToOne()
    @JoinColumn(name = "code")
    private KindPay kindPay;

    @Column(name = "summa")
    private BigDecimal summa;

    @Column(name = "mm")
    private int month;
    @Column(name = "yy")
    private int year;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public KindPay getKindPay() {
        return kindPay;
    }

    public void setKindPay(KindPay kindPay) {
        this.kindPay = kindPay;
    }

    public BigDecimal getSumma() {
        return summa;
    }

    public void setSumma(BigDecimal summa) {
        this.summa = summa;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
