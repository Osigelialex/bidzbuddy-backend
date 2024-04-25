package com.example.biddingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BiddingsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BiddingsystemApplication.class, args);
	}

}
