package com.sec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SecApplication {

    public static void main(String[] args) {
        
//        ConfigurableApplicationContext ctx = SpringApplication.run(SecApplication.class, args);
        SpringApplication.run(SecApplication.class, args);
    }
}
