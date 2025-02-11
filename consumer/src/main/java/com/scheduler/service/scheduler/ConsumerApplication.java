package com.scheduler.service.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsumerApplication {

	public static void main(String[] args) {
		System.out.println("Consumer Startup");
		SpringApplication.run(ConsumerApplication.class, args);
		System.out.println("Consumer Started");
	}

}
