package ru.zhukov.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Gukov on 07.04.2017.
 */
@Entity
@Table(name = "Gtk_Department_Transfer")
public class SetupDepartmentForTransfer {
  @EmbeddedId
  private DepartmentTransferKey departmentTransferKey;

    public DepartmentTransferKey getDepartmentTransferKey() {
        return departmentTransferKey;
    }

    public void setDepartmentTransferKey(DepartmentTransferKey departmentTransferKey) {
        this.departmentTransferKey = departmentTransferKey;
    }
}
