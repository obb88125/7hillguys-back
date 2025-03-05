package com.shinhan.peoch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PeochApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeochApplication.class, args);
	}

}
