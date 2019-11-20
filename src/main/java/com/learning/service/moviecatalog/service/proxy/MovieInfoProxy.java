package com.learning.service.moviecatalog.service.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.learning.service.moviecatalog.model.Movie;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
public class MovieInfoProxy {
	
	Logger logger = LoggerFactory.getLogger(MovieInfoProxy.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	@Value("${movie_catalog.movie_info_service.url}")
	private String movieInfoServiceUrl;
	
	@HystrixCommand(fallbackMethod = "fallbackMovieDetails")
	public Movie getMovieDetails(String movieId) {
		
		
		Movie movie = null;
		
		try {
			StringBuffer uriBuffer = new StringBuffer(movieInfoServiceUrl).append("/").append(movieId);
			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(uriBuffer.toString());
			
			
			ResponseEntity<Movie> movieResponse = restTemplate.getForEntity(builder.toUriString(), Movie.class);
			
			if (movieResponse.getStatusCode()== HttpStatus.OK) {
				logger.info("Got the movie details successfully");
				movie = movieResponse.getBody();
			} else {
				//TODO: Ideally this should not be null but some exception to indicate caller than service call has given error
				return null;
			}
		
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
