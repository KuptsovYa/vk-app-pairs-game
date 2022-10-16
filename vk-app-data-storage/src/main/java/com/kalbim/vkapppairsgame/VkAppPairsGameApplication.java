package com.kalbim.vkapppairsgame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class VkAppPairsGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(VkAppPairsGameApplication.class, args);
	}

}
