package ru.zhukov.domain;

import javax.persistence.*;

/**
 * Created by Gukov on 07.04.2017.
 */
@Entity
@Table(name = "Gtk_Department_Transfer")
public class SetupDepartmentForTransfer {
    @Id

    @Column(name = "id")
    private Long id;
    @Column(name = "From_Department")
    private String fromDepartment;
    @Column(name = "Into_Department")
    private String toDepartment;
    @Column(name = "From_Department_CFO")
    private String departmentCFO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromDepartment() {
        return fromDepartment;
    }

    public void setFromDepartment(String fromDepartment) {
        this.fromDepartment = fromDepartment;
    }

    public String getToDepartment() {
        return toDepartment;
    }

    public void setToDepartment(String toDepartment) {
        this.toDepartment = toDepartment;
    }

    public String getDepartmentCFO() {
        return departmentCFO;
    }

    public void setDepartmentCFO(String departmentCFO) {
        this.departmentCFO = departmentCFO;
    }
}
