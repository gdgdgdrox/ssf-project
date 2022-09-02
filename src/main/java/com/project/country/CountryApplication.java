package com.project.country;

import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CountryApplication {

	@Bean
	public RestTemplate restTemplateGenerator(){
		return new RestTemplate();
	}

	@Bean
	public Random randomObjectGenerator(){
		return new Random();
	}

	public static void main(String[] args) {
		SpringApplication.run(CountryApplication.class, args);
	}

}
