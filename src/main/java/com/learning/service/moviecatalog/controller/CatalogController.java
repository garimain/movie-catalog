package com.learning.service.moviecatalog.controller;



import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learning.service.moviecatalog.common.exception.CatalogSearchMissingMandatoryFieldsException;
import com.learning.service.moviecatalog.model.CatalogSearchBO;
import com.learning.service.moviecatalog.model.MovieCatalog;
import com.learning.service.moviecatalog.service.CatalogService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping ("/api/v1/catalog")
public class CatalogController {
	
	Logger logger = LoggerFactory.getLogger(CatalogController.class);
	
	@Autowired
	CatalogService service;
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Value("${spring.application.name}")
	private String applicationName;

	
	@ApiOperation(value = "Gets the movie catalog for a user", consumes = "user id", response = MovieCatalog.class, notes="This gets watched movie and corresponding ratings for a user")
	@ApiResponses(value = { @ApiResponse (code=HttpStatus.SC_OK, message = "Movie Catalog for the supplied user Id"),
							@ApiResponse (code=HttpStatus.SC_BAD_REQUEST, message = "One of more input not provided not or provided parameters are not valid")
						  })
	@RequestMapping (produces="application/json", method=RequestMethod.GET)
	public MovieCatalog getMovieCatalog(@ApiParam (name = "userId", type = "Number", example = "1", required = true)  @RequestParam (name = "userId", required = true ) String userId) {
		
		logger.info("Catalog Controller - Received request for user {}", userId);
		
		logger.info ("Test for discovery client instances " + discoveryClient.getInstances(applicationName).size());
		
		discoveryClient.getInstances(applicationName).stream().forEach(e->System.out.println(e.getHost()+e.getPort()));
		
		CatalogSearchBO catalogSearchBO = new CatalogSearchBO();
		
		
		if (userId != null) {
			catalogSearchBO.setUserId(userId);
		} else {
			throw new CatalogSearchMissingMandatoryFieldsException("UserId is mandatory for this interface");
		}
		
		MovieCatalog movieCatalog = null;
		movieCatalog = service.getMovieCatalog(catalogSearchBO);
		
		return movieCatalog;
		
		
		
	}
	
	
	

}
