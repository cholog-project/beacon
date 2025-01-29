package com.example.braveCoward;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BraveCowardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BraveCowardApplication.class, args);
	}
}
