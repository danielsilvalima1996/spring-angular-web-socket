package com.example.backendws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BackendwsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendwsApplication.class, args);
	}

}
