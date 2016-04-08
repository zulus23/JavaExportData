package ru.zhukov.dto;

import java.time.LocalDate;

/**
 * Created by Gukov on 08.04.2016.
 */
public class ExportJournal {

    private String code;
    private LocalDate date;
    private String name;
    private String description;

    public ExportJournal(String code, LocalDate date, String name, String description) {
        this.code = code;
        this.date = date;
        this.name = name;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
