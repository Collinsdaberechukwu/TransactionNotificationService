package com.collins.TransactionNotificationService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TransactionNotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionNotificationServiceApplication.class, args);
	}

}
