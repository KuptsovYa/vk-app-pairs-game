package com.kalbim.vkapppairsgame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class VkAppPairsGameApplication {

    public static void main(String[] args) {
        SpringApplication.run(VkAppPairsGameApplication.class, args);
    }

}
