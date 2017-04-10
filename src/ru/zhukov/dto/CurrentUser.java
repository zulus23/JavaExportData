package ru.zhukov.dto;

import com.sun.istack.internal.NotNull;
import org.springframework.context.annotation.Bean;
import ru.zhukov.domain.Database;

/**
 * Created by Zhukov on 22.03.2016.
 */

public class CurrentUser {

    @NotNull
    private String username;

    @NotNull
    private Database database;

    private String password;




    public CurrentUser(String username, String password, Database database){
        this.username = username;
        this.password = password;
        this.database = database;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Database getDatabase() {
        return database;
    }


    

}
