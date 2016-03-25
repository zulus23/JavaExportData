package ru.zhukov.dto;

import com.sun.istack.internal.NotNull;
import ru.zhukov.domain.Database;

/**
 * Created by Zhukov on 22.03.2016.
 */
public class CurrentUser {

    @NotNull
    private String username;

    @NotNull
    private Database database;


    public CurrentUser(String username, Database database){
        this.username = username;
        this.database = database;
    }

    public String getUsername() {
        return username;
    }

    public Database getDatabase() {
        return database;
    }


    

}
