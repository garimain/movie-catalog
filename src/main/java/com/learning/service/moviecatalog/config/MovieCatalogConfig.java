package com.learning.service.moviecatalog.config;

import java.util.Collection;
import java.util.Collections;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class MovieCatalogConfig {
	
	@Bean
	@LoadBalanced
    RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public Docket swaggerConfiguration() {
		
		return new Docket((DocumentationType.SWAGGER_2))
				.select()
				.paths(PathSelectors.ant("/api/*/catalog/*"))
				.apis(RequestHandlerSelectors.basePackage("com.learning"))
				.build()
				.apiInfo(apiDetails());
		
				
	}
	
	private ApiInfo apiDetails() {
		return new ApiInfo ("Movie Catalog Service", "This is Movie Catalog Service for checking movie ratings", "1.0", 
				"free to use", new springfox.documentation.service.Contact("Garima Kapoor", "http://learning.service", "garimamca@yahoo.com"), 
				"API License", "http://learning.service", Collections.EMPTY_LIST);
				
	}
	
	
	
	
	
}
