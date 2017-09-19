package ru.zhukov.domain;



import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "dol_unit")
public class StaffTable {
    @Id
    @Column(name = "kod")
    private String id;
    @Column(name = "oklad_pers")
    private BigDecimal salary;

    @ManyToOne
    @JoinColumn(name = "pers_id")
    private Employee employee;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
