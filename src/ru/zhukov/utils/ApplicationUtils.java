package ru.zhukov.utils;


import net.sourceforge.jtds.jdbcx.JtdsDataSource;
import ru.zhukov.dto.CurrentUser;

import javax.sql.DataSource;

/**
 * Created by Gukov on 30.03.2016.
 */
public class ApplicationUtils {

    public  static JtdsDataSource setupServerAndHostDb(CurrentUser currentUser, JtdsDataSource dataSource) {
        dataSource.setDatabaseName(currentUser.getDatabase().getNameInDB());
        System.getenv().entrySet().stream().filter(e -> e.getKey().contains("HOSTNAME")|| e.getKey().contains("COMPUTERNAME"))
                .map(e->e.getValue())
                .findFirst().ifPresent(e -> {
            if (e.toUpperCase().contains("Zhukov-PC".toUpperCase())) {
                dataSource.setServerName("Zhukov-PC");
                dataSource.setInstance("MSSQL2014_DEV");


            }else{

                dataSource.setServerName("SRV-SQLBOX");
                dataSource.setInstance("AIT");

            }});

        return dataSource;
    }
}
