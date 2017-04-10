package ru.zhukov.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Zhukov on 10.04.2017.
 */
@Embeddable
public class DepartmentTransferKey implements Serializable{
    @Column
    private String fromDepartment;
    @Column
     private String toDepartment;

    public DepartmentTransferKey() {
    }

    public DepartmentTransferKey(String fromDepartment, String toDepartment) {
        this.fromDepartment = fromDepartment;
        this.toDepartment = toDepartment;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DepartmentTransferKey)) return false;

        DepartmentTransferKey that = (DepartmentTransferKey) o;

        if (!fromDepartment.equals(that.fromDepartment)) return false;
        return toDepartment.equals(that.toDepartment);
    }

    @Override
    public int hashCode() {
        int result = fromDepartment.hashCode();
        result = 31 * result + toDepartment.hashCode();
        return result;
    }
}
