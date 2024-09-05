package com.example.biddingsystem;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition(
		info = @Info(
				title = "Bidzbuddy API",
				version = "1.0",
				description = "Bidzbuddy API documentation"
		)
)
public class BiddingsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BiddingsystemApplication.class, args);
	}

}
