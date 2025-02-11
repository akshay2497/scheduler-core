package com.scheduler.service.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProducerApplication {

	public static void main(String[] args) {
		System.out.println("ProducerApplication Startup...");
		SpringApplication.run(ProducerApplication.class, args);
		System.out.println("ProducerApplication Started!!!");
	}

}
