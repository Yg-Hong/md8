package com.ms8.md.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BackendGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendGatewayApplication.class, args);
	}

}
