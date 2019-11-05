package com.learning.service.moviecatalog.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.learning.service.moviecatalog.model.Movie;
import com.learning.service.moviecatalog.model.MovieCatalog;
import com.learning.service.moviecatalog.model.MovieRating;
import com.learning.service.moviecatalog.model.MovieRatingDetails;
import com.learning.service.moviecatalog.model.UserMovieRating;
import com.learning.service.moviecatalog.service.CatalogService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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
	@RequestMapping (path = "/{userId}", produces="application/json", method=RequestMethod.GET)
	public MovieCatalog getMovieCatalog(@ApiParam(name = "userId", example = "123", value = "user id for the user") @PathVariable (name = "userId" ) String userId) {
		
		logger.info("Received request for user {}", userId);
		
		logger.info ("Test for discovery client instances " + discoveryClient.getInstances(applicationName).size());
		
		discoveryClient.getInstances(applicationName).stream().forEach(e->System.out.println(e.getHost()+e.getPort()));
		
		MovieCatalog movieCatalog = new MovieCatalog();
		movieCatalog.setUserId(userId);
		List<MovieRatingDetails> movieRatingDetails  = null;
		
		
		
		//Step 1 - invoke rating service to get the movie id and ratings for a given user id
		UserMovieRating userMovieRating = service.getMovieRatings(userId);
		
		//replace this with optional
		
		if (userMovieRating != null && userMovieRating.getListMovie() != null) {
			
			movieRatingDetails = getMovieRatingDetails(userMovieRating);
			
			movieCatalog.setMovieRatingDetails(movieRatingDetails);
		}
		
		logger.info("Response processed for user {}", userId);
		
		return movieCatalog;
		
	}
	
	private List<MovieRatingDetails> getMovieRatingDetails(UserMovieRating userMovieRating) {
		
		return userMovieRating.getListMovie().stream().map(mr-> {
			Movie movie = service.getMovieDetails(mr.getMovieId());
			MovieRatingDetails movieRatingDetail = new MovieRatingDetails(movie.getName(), movie.getInformation(), mr.getRating());
			return movieRatingDetail;
		
		}).collect(Collectors.toList());
		
	}
	
	
	

}
