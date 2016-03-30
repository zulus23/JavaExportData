package ru.zhukov.config;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
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



}
