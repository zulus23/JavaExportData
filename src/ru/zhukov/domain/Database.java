package ru.zhukov.domain;

/**
 * Created by Zhukov on 15.03.2016.
 */
public class Database {

    private final String name;
    private final String nameInDB;
    private final String nameinAxapta;

    public Database(String name, String nameInDB, String nameinAxapta) {
        this.name = name;
        this.nameInDB = nameInDB;
        this.nameinAxapta = nameinAxapta;
    }

    public String getName() {
        return name;
    }

    public String getNameInDB() {
        return nameInDB;
    }

    public String getNameinAxapta() {
        return nameinAxapta;
    }


    @Override
    public String toString() {
        return "Database{" +
                "name='" + name + '\'' +
                ", nameInDB='" + nameInDB + '\'' +
                ", nameinAxapta='" + nameinAxapta + '\'' +
                '}';
    }
}
