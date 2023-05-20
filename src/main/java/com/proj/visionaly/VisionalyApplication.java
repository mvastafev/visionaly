package com.proj.visionaly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



//we can use only SpringBootApp annotation instead of following 3 annotations
//@EnableAutoConfiguration
//@EntityScan("com.proj.visionaly.models")
//@EnableJpaRepositories("com.proj.visionaly.repository")
@SpringBootApplication
public class VisionalyApplication {

	public static void main(String[] args) {
		SpringApplication.run(VisionalyApplication.class, args);
	}

}
