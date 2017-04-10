package ru.zhukov.config;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import ru.zhukov.dto.CurrentUser;
import ru.zhukov.utils.ApplicationUtils;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Created by Gukov on 28.03.2016.
 */

@Configuration

public class ApplicationContextConfig{


    private static CurrentUser currentUser;

    @Bean
    public static LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource){
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setJpaVendorAdapter(new EclipseLinkJpaVendorAdapter());
        factoryBean.setPackagesToScan("ru.zhukov.entity","ru.zhukov.dto");
        return  factoryBean;
    }

    @Bean
    public static DataSource dataSource( CurrentUser currentUser ){
        ApplicationContextConfig.currentUser = currentUser;
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
