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
    private String dimensionDB;

    public ExportJournal(String code, LocalDate date, String name, String description, String dimensionDB) {
        this.code = code;
        this.date = date;
        this.name = name;
        this.description = description;
        this.dimensionDB = dimensionDB;
    }

    public String getCode() {
        return code;
    }
    public LocalDate getDate() {
        return date;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getDimensionDB() {
        return dimensionDB;
    }


}
