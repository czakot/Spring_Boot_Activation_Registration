/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sec.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Level;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 *
 * @author czakot
 */
@Configuration
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourceConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private long connectionTimeout;
    private String prefix;
    private String[] hosts;
    private String port;
    private String dbname;
    private String username;
    private String password;

    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource getDataSource() {

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setConnectionTimeout(connectionTimeout);
//        dataSource.setInitializationFailTimeout(15); // default: 1
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        for (String host : hosts) {
            String url = prefix + "://" + host + ":" + port + "/" + dbname;
            logger.info("Testing DB connection: " + url);
            dataSource.setJdbcUrl(url);
            try {
                dataSource.getConnection();
                return dataSource;
            } catch (SQLException ex) {
            }
        }
        logger.error("No available DB host");
        //TODO Create embedded DB
        return DataSourceBuilder.create().build();
    }

    public void setConnectionTimeout(long connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setHosts(String[] hosts) {
        this.hosts = hosts;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
