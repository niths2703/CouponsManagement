package com.example.CouponsManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CouponsManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(CouponsManagementApplication.class, args);
	}

}
