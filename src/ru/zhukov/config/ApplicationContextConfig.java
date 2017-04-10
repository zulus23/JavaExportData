package ru.zhukov.config;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import ru.zhukov.dto.CurrentUser;
import ru.zhukov.utils.ApplicationUtils;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Gukov on 28.03.2016.
 */

@Configuration
@EnableJpaRepositories(basePackages = "ru.zhukov.repository")
@ComponentScan(basePackages = "ru.zhukov.repository")
public class ApplicationContextConfig{


    private static CurrentUser currentUser;

    @Bean
    public static LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource){
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        EclipseLinkJpaVendorAdapter jpaVendorAdapter = new EclipseLinkJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(true);
        jpaVendorAdapter.setGenerateDdl(true);
        Map props = new HashMap();
        props.put(PersistenceUnitProperties.DDL_SQL_SCRIPT_GENERATION,"sql-script");
       // props.put(PersistenceUnitProperties.DDL_GENERATION_MODE, PersistenceUnitProperties.DDL_BOTH_GENERATION);
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        factoryBean.setPackagesToScan("ru.zhukov.domain");
        factoryBean.setJpaPropertyMap(props);

        factoryBean.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
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

    @Bean
    public static CurrentUser currentUser(){
        return  currentUser;
    }

    public static void setCurrentUser(CurrentUser currentUser) {
        ApplicationContextConfig.currentUser = currentUser;
    }
    @Bean
    public InstrumentationLoadTimeWeaver loadTimeWeaver()  throws Throwable {
        InstrumentationLoadTimeWeaver loadTimeWeaver = new InstrumentationLoadTimeWeaver();
        return loadTimeWeaver;
    }
}
