package com.proj.visionaly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.proj.visionaly.models.*")
@EnableJpaRepositories("com.proj.visionaly.repository")
public class VisionalyApplication {

	public static void main(String[] args) {
		SpringApplication.run(VisionalyApplication.class, args);
	}

}
