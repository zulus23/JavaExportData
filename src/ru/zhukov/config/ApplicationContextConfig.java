package ru.zhukov.config;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.zhukov.dto.CurrentUser;

import javax.sql.DataSource;

/**
 * Created by Gukov on 28.03.2016.
 */
@Configuration
@ComponentScan(basePackages = {"ru.zhukov"})
public class ApplicationContextConfig{


    @Bean
    @Autowired
    public DataSource dataSource( CurrentUser currentUser){
        SQLServerDataSource dataSource = new SQLServerDataSource();

        dataSource.setDatabaseName(currentUser.getDatabase().getNameInDB());
        System.getenv().entrySet().stream().filter(e -> e.getKey().contains("HOSTNAME")|| e.getKey().contains("COMPUTERNAME"))
                .map(e->e.getValue())
                .findFirst().ifPresent(e -> {
            if (e.toUpperCase().contains("Zhukov-PC".toUpperCase())) {
                dataSource.setServerName("Zhukov-PC");
                dataSource.setInstanceName("MSSQLSERVER2012");


            }else{

                dataSource.setServerName("SRV-SQLBOX");
                dataSource.setInstanceName("AIT");

            }});

        dataSource.setUser(currentUser.getUsername());
        dataSource.setPassword(currentUser.getPassword());
        return dataSource;
    }

}
