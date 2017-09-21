package ru.zhukov.domain;


import javax.persistence.*;
import java.io.Serializable;


@Embeddable
public class TariffId implements Serializable{
    @Column(name = "level_row")
    private Integer number;
    @Column(name = "step_column")
    private Integer rank;

    public TariffId() {
    }

    public TariffId(Integer number, Integer rank) {
        this.number = number;
        this.rank = rank;
    }

    public Integer getNumber() {
        return number;
    }

    public Integer getRank() {
        return rank;
    }
}
