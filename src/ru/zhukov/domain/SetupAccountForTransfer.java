package ru.zhukov.domain;


import javax.persistence.*;

/**
 * Created by Gukov on 07.04.2017.
 */
@Entity
@Table(name = "Gtk_Account_Transfer")
public class SetupAccountForTransfer {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "input_from_ait")
    private String accountOut;
    @Column(name = "output_to_1c")
    private String accountIn;
    @Column(name = "analytics_property_one")
    private String nameAnalytic;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountOut() {
        return accountOut;
    }

    public void setAccountOut(String accountOut) {
        this.accountOut = accountOut;
    }

    public String getAccountIn() {
        return accountIn;
    }

    public void setAccountIn(String accountIn) {
        this.accountIn = accountIn;
    }

    public String getNameAnalytic() {
        return nameAnalytic;
    }

    public void setNameAnalytic(String nameAnalytic) {
        this.nameAnalytic = nameAnalytic;
    }

    @Override
    public String toString() {
        return "SetupAccountForTransfer{" +
                "id=" + id +
                ", accountOut='" + accountOut + '\'' +
                ", accountIn='" + accountIn + '\'' +
                ", nameAnalytic='" + nameAnalytic + '\'' +
                '}';
    }
}
