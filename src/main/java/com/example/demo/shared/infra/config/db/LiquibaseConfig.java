package com.example.demo.shared.infra.config.db;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfig {

    /**
     * PostgreSQL config
     * **/
    @Bean
    @ConfigurationProperties("spring.datasource.postgresql.liquibase")
    public LiquibaseProperties postgresqlLiquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean
    public SpringLiquibase postgresqlLiquibase(
            @Qualifier("postgresqlDataSource") DataSource postgresqlDataSource,
            @Qualifier("postgresqlLiquibaseProperties") LiquibaseProperties postgresqlLiquibaseProperties
    ) {
        return springLiquibase(postgresqlDataSource, postgresqlLiquibaseProperties);
    }

    /**
     * MySQL config
     * **/
    @Bean
    @ConfigurationProperties("spring.datasource.mysql.liquibase")
    public LiquibaseProperties mysqlLiquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean
    public SpringLiquibase mysqlLiquibase(
            @Qualifier("mysqlDataSource") DataSource mysqlDataSource,
            @Qualifier("mysqlLiquibaseProperties") LiquibaseProperties mysqlLiquibaseProperties
    ) {
        return springLiquibase(mysqlDataSource, mysqlLiquibaseProperties);
    }

    private static SpringLiquibase springLiquibase(DataSource dataSource, LiquibaseProperties properties) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(properties.getChangeLog());
        liquibase.setContexts(properties.getContexts());
        liquibase.setDefaultSchema(properties.getDefaultSchema());
        liquibase.setDropFirst(properties.isDropFirst());
        liquibase.setShouldRun(properties.isEnabled());
        liquibase.setLabels(properties.getLabels());
        liquibase.setChangeLogParameters(properties.getParameters());
        liquibase.setRollbackFile(properties.getRollbackFile());
        return liquibase;
    }

}
