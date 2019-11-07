package com.learning.service.moviecatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;


import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
@EnableCircuitBreaker
@EnableHystrixDashboard
public class MovieCatalogApplication {
	

	
	
	public static void main(String[] args) {
		SpringApplication.run(MovieCatalogApplication.class, args);
		
		
	}
	
	

}
