package com.vladzyatkovski.task_for_solva;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class MicroserviceForSolvaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceForSolvaApplication.class, args);
	}

}
