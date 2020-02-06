package com.sec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SecApplication {

    private static String[] args;
    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {

        SecApplication.args = args;
        SecApplication.context = SpringApplication.run(SecApplication.class, args);
    }

    public static void restart() {

        context.close();
        SecApplication.context = SpringApplication.run(SecApplication.class, args);
    }
}
