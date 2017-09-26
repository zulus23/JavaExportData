package ru.zhukov.domain;




import javax.persistence.*;

@Entity
@Table(name = "xcheck")
public class Department {
    @Id
    @Column(name = "n_otd")
    private String id;
    @Column(name = "naz_otd")
    private String name;

    @Column(name = "brief_name")
    private String briefName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBriefName() {
        return briefName;
    }

    public void setBriefName(String briefName) {
        this.briefName = briefName;
    }
}
