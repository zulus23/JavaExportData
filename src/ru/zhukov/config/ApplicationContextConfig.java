package ru.zhukov.config;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import ru.zhukov.dto.CurrentUser;
import ru.zhukov.utils.ApplicationUtils;

import javax.sql.DataSource;

/**
 * Created by Gukov on 28.03.2016.
 */

public class ApplicationContextConfig{



    public static DataSource dataSource( CurrentUser currentUser){
        SQLServerDataSource dataSource = new SQLServerDataSource();

        dataSource = ApplicationUtils.setupServerAndHostDb(currentUser, dataSource);

        dataSource.setUser(currentUser.getUsername());
        dataSource.setPassword(currentUser.getPassword());
        return dataSource;
    }
    public static DataSource dataSourceAxapta(){
        SQLServerDataSource dataSource = new SQLServerDataSource();

        dataSource.setServerName("SRV-AXDB");
        dataSource.setDatabaseName("DBImpExp");
        //dataSource.setInstanceName("MSSQLSERVER2012");

        dataSource.setUser("dbimpexp");
        dataSource.setPassword("dbimpexp");
        return dataSource;
    }




}
