package ru.zhukov.domain;


import javax.persistence.*;

@Entity
@Table(name = "dolsn")
public class Position {
    @Id
    @Column(name = "dolsn")
    private String id;
    @Column(name = "name_dolsn")
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
