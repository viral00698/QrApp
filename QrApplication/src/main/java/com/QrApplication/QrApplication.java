package com.QrApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class QrApplication {

	public static void main(String[] args) {
		SpringApplication.run(QrApplication.class, args);
	}

}
