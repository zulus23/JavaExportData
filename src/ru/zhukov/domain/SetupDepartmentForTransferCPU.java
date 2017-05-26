package ru.zhukov.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Gukov on 07.04.2017.
 */


public class SetupDepartmentForTransferCPU {

    @Column(name = "account1c")
    private  String accountIn;
    @Column(name = "cbe")
    private String cbe;
    @Column(name = "accountAit")
    private  String accountAit;

    public String getAccountIn() {
        return accountIn;
    }

    public void setAccountIn(String accountIn) {
        this.accountIn = accountIn;
    }

    public String getCbe() {
        return cbe;
    }

    public void setCbe(String cbe) {
        this.cbe = cbe;
    }

    public String getAccountAit() {
        return accountAit;
    }

    public void setAccountAit(String accountAit) {
        this.accountAit = accountAit;
    }
}
