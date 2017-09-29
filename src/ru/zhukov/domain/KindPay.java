package ru.zhukov.domain;



import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sprav")
public class KindPay  implements Serializable{
    @Id
    @Column(name = "code")
    private String code;
    @Column(name = "codename")
    private String name;
    @Column(name = "finoper")
    private String financeOperation;
    @Column(name = "tip")
    private String tip;

    @Column(name = "vid_dohods")
    private String codeProfit;





    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCodeProfit() {
        return codeProfit;
    }

    public void setCodeProfit(String codeProfit) {
        this.codeProfit = codeProfit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KindPay kindPay = (KindPay) o;

        return getCode() != null ? getCode().equals(kindPay.getCode()) : kindPay.getCode() == null;
    }

    @Override
    public int hashCode() {
        return getCode() != null ? getCode().hashCode() : 0;
    }

    @Override
    public String toString() {
        return String.format("%s - %s",code,name);
    }
}
