package com.project.devgram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DevgramApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevgramApplication.class, args);
	}

}
