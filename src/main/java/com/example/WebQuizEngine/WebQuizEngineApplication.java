package com.example.WebQuizEngine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class WebQuizEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebQuizEngineApplication.class, args);
	}
}
