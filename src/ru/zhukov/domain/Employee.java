package ru.zhukov.domain;



import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "const")
public class Employee {
    @Id
    @Column(name = "pers_id")
    private String id;
    @Column(name = "tabel_n")
    private String tabelNumber;
    @Column(name = "name")
    private String name;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "sec_name")
    private String secondName;
    @Column(name = "oklad")
    private BigDecimal salary;
    @OneToMany(mappedBy = "employee")
    private List<StaffTable> staffTable;

    @ManyToOne
    @JoinColumn(name = "n_otd")
    private Department department;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTabelNumber() {
        return tabelNumber;
    }

    public void setTabelNumber(String tabelNumber) {
        this.tabelNumber = tabelNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }


    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<StaffTable> getStaffTable() {
        return staffTable;
    }

    public void setStaffTable(List<StaffTable> staffTable) {
        this.staffTable = staffTable;
    }
}
