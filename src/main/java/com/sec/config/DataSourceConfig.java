/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sec.config;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
public class DataSourceConfig {

    @Value("${spring.datasource.prefix}")
    String prefix;
    @Value("${spring.datasource.hosts}")
    String[] hosts;
    @Value("${spring.datasource.port}")
    String port;
    @Value("${spring.datasource.dbname}")
    String dbname;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;

    @Bean
    public DataSource getDataSource() {

        Logger logger = LoggerFactory.getLogger(this.getClass());
        DataSource dataSource;

        for (String host : hosts) {
            if (hostReachable(host)) {

                String url = prefix + "://" + host + ":" + port + "/" + dbname;
                logger.info("Testing DB connection: " + url);
                try {
                    dataSource = DataSourceBuilder
                        .create()
                        .url(url)
                        .username(username)
                        .password(password)
                        .build();
                    dataSource.getConnection();
                    return dataSource;
                } catch (SQLException sQLException) {
                    /* nothing, just step next */ }
            }
        }

        return DataSourceBuilder.create().build();
    }

    private boolean hostReachable(String host) {
        boolean hostReachable = false;
        try {
            if (Character.isLetter(host.charAt(0))) {
                hostReachable = InetAddress.getByName(host).isReachable(500);
            } else {
                byte[] ip = {
                    (byte)Integer.parseInt(host.substring(0, 2)),
                    (byte)Integer.parseInt(host.substring(2, 2)),
                    (byte)Integer.parseInt(host.substring(4, 2)),
                    (byte)Integer.parseInt(host.substring(6, 2)),
                };
                hostReachable = InetAddress.getByAddress(ip).isReachable(500);
            }
        } catch (IOException ex) {     }
        return hostReachable;
    }
}
