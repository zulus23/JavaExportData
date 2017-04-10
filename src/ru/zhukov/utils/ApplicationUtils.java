package ru.zhukov.utils;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import ru.zhukov.dto.CurrentUser;

import javax.sql.DataSource;

/**
 * Created by Gukov on 30.03.2016.
 */
public class ApplicationUtils {

    public  static SQLServerDataSource setupServerAndHostDb(CurrentUser currentUser, SQLServerDataSource dataSource) {
        dataSource.setDatabaseName(currentUser.getDatabase().getNameInDB());
        System.getenv().entrySet().stream().filter(e -> e.getKey().contains("HOSTNAME")|| e.getKey().contains("COMPUTERNAME"))
                .map(e->e.getValue())
                .findFirst().ifPresent(e -> {
            if (e.toUpperCase().contains("Zhukov-PC".toUpperCase())) {
                dataSource.setServerName("Zhukov-PC");
                dataSource.setInstanceName("MSSQL2014_DEV");


            }else{

                dataSource.setServerName("SRV-SQLBOX");
                dataSource.setInstanceName("AIT");

            }});

        return dataSource;
    }
}
