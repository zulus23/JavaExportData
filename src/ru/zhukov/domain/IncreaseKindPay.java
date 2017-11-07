package ru.zhukov.domain;



import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Gtk_IncreaseKindPay")
public class IncreaseKindPay implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;


    @OneToOne
    @JoinColumn(name = "code")
    private KindPay kindPay;
    @ManyToOne
    @JoinColumn(name = "codePay")
    private KindPay kindPayIn;

    public KindPay getKindPay() {
        return kindPay;
    }

    public void setKindPay(KindPay kindPay) {
        this.kindPay = kindPay;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public KindPay getKindPayIn() {
        return kindPayIn;
    }

    public void setKindPayIn(KindPay kindPayIn) {
        this.kindPayIn = kindPayIn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IncreaseKindPay that = (IncreaseKindPay) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }


    @Override
    public String toString() {
        return String.format("%s - %s",kindPay.getCode(),kindPay.getName());
    }
}
