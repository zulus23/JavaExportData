package ru.zhukov.domain;



import javax.persistence.*;

@Entity
@Table(name = "Gtk_IncreaseKindPay")
public class IncreaseKindPay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;


    @OneToOne
    @JoinColumn(name = "code")
    private KindPay kindPay;


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
}
