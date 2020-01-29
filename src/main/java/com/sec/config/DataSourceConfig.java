/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sec.config;

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

    return DataSourceBuilder.create().build();
  }
}
