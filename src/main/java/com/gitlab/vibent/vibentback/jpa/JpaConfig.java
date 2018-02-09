package com.gitlab.vibent.vibentback.jpa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class JpaConfig {

    private static final String DB_NAME = "VIBENT_BACK";

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/" + DB_NAME + "?createDatabaseIfNotExist=true");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        return dataSource;
    }

}
