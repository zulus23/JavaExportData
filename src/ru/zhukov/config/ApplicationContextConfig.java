package ru.zhukov.config;


import com.sun.org.apache.xpath.internal.operations.Bool;
import net.sourceforge.jtds.jdbcx.JtdsDataSource;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.zhukov.domain.Database;
import ru.zhukov.dto.CurrentUser;
import ru.zhukov.utils.ApplicationUtils;

import javax.persistence.*;
import javax.sql.DataSource;
import javax.transaction.TransactionManager;
import java.util.*;

/**
 * Created by Gukov on 28.03.2016.
 */

@Configuration
@ComponentScan(basePackages = {"ru.zhukov.domain","ru.zhukov.service","ru.zhukov.repository"})
@EnableJpaRepositories(basePackages = "ru.zhukov.repository")
@EnableTransactionManagement
public class ApplicationContextConfig{


    private static CurrentUser currentUser;

    @Bean
    public static LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource){
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        Map<String,Object> map =  new HashMap<>();
        map.putAll(factoryBean.getJpaPropertyMap());

        map.put(PersistenceUnitProperties.WEAVING,"false");
        factoryBean.setJpaPropertyMap(map);
        factoryBean.setDataSource(dataSource);
        EclipseLinkJpaVendorAdapter jpaVendorAdapter = new EclipseLinkJpaVendorAdapter();
       // HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();

        jpaVendorAdapter.setShowSql(true);

        jpaVendorAdapter.setGenerateDdl(false);
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        factoryBean.setPackagesToScan("ru.zhukov.domain","ru.zhukov.utils");

        factoryBean.setPersistenceUnitName("testUnit");
        //factoryBean.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
        return factoryBean;

    }

    @Bean
    public static DataSource dataSource( CurrentUser currentUser ){
        currentUser = Optional.ofNullable(currentUser).orElseGet(() -> {
            return  new CurrentUser("report","report",new Database("","ait_db",""));

        });

        ApplicationContextConfig.currentUser = currentUser;

        JtdsDataSource dataSource = new JtdsDataSource();

        dataSource = ApplicationUtils.setupServerAndHostDb(currentUser, dataSource);

        dataSource.setUser(currentUser.getUsername());
        dataSource.setPassword(currentUser.getPassword());
        return dataSource;
    }
    public static DataSource dataSourceAxapta(){
        JtdsDataSource dataSource = new JtdsDataSource();

        dataSource.setServerName("SRV-AXDB");
        dataSource.setDatabaseName("DBImpExp");
        //dataSource.setInstanceName("MSSQLSERVER2012");

        dataSource.setUser("dbimpexp");
        dataSource.setPassword("dbimpexp");
        return dataSource;
    }

   @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }


    @Bean
    public static CurrentUser currentUser(){
        return  currentUser;
    }

    public static void setCurrentUser(CurrentUser currentUser) {
        ApplicationContextConfig.currentUser = currentUser;
    }
   /* @Bean
    public InstrumentationLoadTimeWeaver loadTimeWeaver()  throws Throwable {
        InstrumentationLoadTimeWeaver loadTimeWeaver = new InstrumentationLoadTimeWeaver();
        return loadTimeWeaver;
    }*/
}
