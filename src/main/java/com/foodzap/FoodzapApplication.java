package com.foodzap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.foodzap.repositories")
public class FoodzapApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodzapApplication.class, args);
	}

}
