package ru.zhukov.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Gukov on 27.04.2017.
 */
@Entity
@Table(name = "Gtk_CostItem_Transfer")
public class SetupCostItemTransfer {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "input_from_ait")
    private String zatrFromAit;
    @Column(name = "output_to_1c")
    private String zatrTo1C;
    @Column(name = "debit")
    private String debit;
    @Column(name = "credit")
    private String credit;
    @Column(name = "code_pay_ait")
    private String code;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZatrFromAit() {
        return zatrFromAit;
    }

    public void setZatrFromAit(String zatrFromAit) {
        this.zatrFromAit = zatrFromAit;
    }

    public String getZatrTo1C() {
        return zatrTo1C;
    }

    public void setZatrTo1C(String zatrTo1C) {
        this.zatrTo1C = zatrTo1C;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
