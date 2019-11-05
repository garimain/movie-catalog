package com.learning.service.moviecatalog.service;



import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.learning.service.moviecatalog.model.Movie;
import com.learning.service.moviecatalog.model.MovieRating;
import com.learning.service.moviecatalog.model.UserMovieRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class CatalogService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	Logger logger = LoggerFactory.getLogger(CatalogService.class);
	
	
	@HystrixCommand(fallbackMethod = "getFallbackMovieRatings")
	public UserMovieRating getMovieRatings(String userId) {
		
		UserMovieRating userMovieRating = null;
		
		
		try {
		
			userMovieRating = 
				restTemplate.getForObject("http://user-movie-rating-service/movie-ratings/"+userId, UserMovieRating.class);
			logger.info("Got the user movie ratings successfully");
		
		} catch (Exception ex) {
			
			logger.error("Exception occured while calling user movie rating service {} ", ex.getMessage());
			throw ex;
			
		}
		
		return userMovieRating;
		
	}
	
	private UserMovieRating getFallbackMovieRatings(String userId) {
		
		List<MovieRating> movieRatings =  Arrays.asList(new MovieRating(userId, "100", 5));
		return new UserMovieRating(userId, movieRatings);
		
	}
	
	@HystrixCommand(fallbackMethod = "fallbackMovieDetails")
	public Movie getMovieDetails(String movieId) {
		
		
		Movie movie = null;
		
		try {
		
			movie = 
				restTemplate.getForObject("http://movie-info-service/movies/"+movieId, Movie.class);
		
		} catch (Exception ex) {
			
			logger.error("Exception occured while calling user movie service {} ", ex.getMessage());
			throw ex;
			
		}
		
		return movie;
		
	}
	
	private Movie fallbackMovieDetails(String movieId) {
		
		return new Movie(movieId, "Name not available", "No Info Exists" );
		
		
	}
	
}
