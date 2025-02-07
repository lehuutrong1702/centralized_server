package com.example.centralized_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CentralizedServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CentralizedServerApplication.class, args);
	}

}
