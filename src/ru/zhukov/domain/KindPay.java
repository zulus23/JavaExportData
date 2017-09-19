package ru.zhukov.domain;



import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;

@Entity
@Table(name = "sprav")
public class KindPay {
    @Id
    @Column(name = "code")
    private String code;
    @Column(name = "codename")
    private String name;

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
}
