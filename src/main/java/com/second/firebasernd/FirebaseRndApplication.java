package com.second.firebasernd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FirebaseRndApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirebaseRndApplication.class, args);
	}

}
