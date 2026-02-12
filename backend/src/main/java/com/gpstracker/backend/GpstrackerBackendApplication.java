package com.gpstracker.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GpstrackerBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(GpstrackerBackendApplication.class, args);
	}
}
