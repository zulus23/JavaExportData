package ru.zhukov.service;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.sun.istack.internal.NotNull;

import ru.zhukov.domain.Database;
import ru.zhukov.dto.CurrentUser;
import ru.zhukov.service.auth.Authentication;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Zhukov on 22.03.2016.
 */
public class DBAuthenticationService implements Authentication {

    @NotNull
    private CurrentUser currentUser;

    private CompletableFuture login;

    public DBAuthenticationService() {

    }


    @Override
    public CompletableFuture<CurrentUser> authentication(String username, String password, Database database) {

        return checkLogin(username, password, database).thenApply(u-> {
            u.ifPresent( e ->  currentUser =new CurrentUser(e.getUsername(),e.getDatabase()));
            return currentUser;

        });



    }

    private CompletableFuture<Optional<CurrentUser>> checkLogin(String username, String password, Database database){
        return  CompletableFuture.supplyAsync(()-> {
                   /*
                    SQLServerDataSource sqlServerDataSource = new SQLServerDataSource();
                    sqlServerDataSource.setDatabaseName(database.getNameInDB());
                    sqlServerDataSource.setServerName("SRV-SQLBOX");
                    sqlServerDataSource.setInstanceName("AIT");
                    sqlServerDataSource.setUser(username);
                    sqlServerDataSource.setPassword(password);*/
                    SQLServerDataSource sqlServerDataSource = new SQLServerDataSource();
                    sqlServerDataSource.setDatabaseName(database.getNameInDB());
                    sqlServerDataSource.setServerName("Zhukov-PC");
                    sqlServerDataSource.setInstanceName("MSSQLSERVER2012");
                    sqlServerDataSource.setUser(username);
                    sqlServerDataSource.setPassword(password);

            try (Connection connection = sqlServerDataSource.getConnection()) {


                        return  Optional.of(new CurrentUser(username, database));
                    } catch (SQLException e) {

                        return  Optional.empty();

                    }
                }
        );
    }
}
