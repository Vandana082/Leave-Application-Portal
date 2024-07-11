package com.example.LeaveApplicationPortal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class LeaveApplicationPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeaveApplicationPortalApplication.class, args);
	}

}
