package com.hh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(exclude =
org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class)
public class LaptophditApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaptophditApplication.class, args);
	}
}
