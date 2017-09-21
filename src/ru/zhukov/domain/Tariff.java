package ru.zhukov.domain;




import javax.jdo.annotations.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "ets")
public class Tariff {
   @EmbeddedId
   private TariffId id;

   @Column(name = "summa")
   private BigDecimal summa;

    public TariffId getId() {
        return id;
    }

    public void setId(TariffId id) {
        this.id = id;
    }

    public BigDecimal getSumma() {
        return summa;
    }

    public void setSumma(BigDecimal summa) {
        this.summa = summa;
    }

    public Integer getNumberTarif(){
        return id.getNumber();
    }
    public Integer getRank(){
        return id.getRank();
    }
}
