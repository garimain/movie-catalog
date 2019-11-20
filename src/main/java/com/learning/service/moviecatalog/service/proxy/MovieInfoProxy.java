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

import com.learning.service.moviecatalog.model.MovieBO;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
public class MovieInfoProxy {
	
	Logger logger = LoggerFactory.getLogger(MovieInfoProxy.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	@Value("${movie_catalog.movie_info_service.url}")
	private String movieInfoServiceUrl;
	
	@HystrixCommand(fallbackMethod = "fallbackMovieDetails")
	public MovieBO getMovieDetails(Integer movieId) {
		
		logger.info("MovieInfoProxy - Received request for movieId {}", movieId);
		
		MovieBO movie = null;
		
		try {
			StringBuffer uriBuffer = new StringBuffer(movieInfoServiceUrl).append("/").append(movieId.toString());
			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(uriBuffer.toString());
			
			logger.info("MovieInfoProxy - request being made at  {} ", builder.toUriString());
			
			ResponseEntity<MovieBO> movieResponse = restTemplate.getForEntity(builder.toUriString(), MovieBO.class);
			
			if (movieResponse.getStatusCode()== HttpStatus.OK) {
				logger.info("MovieInfoProxy - Got the user movie ratings successfully {} " + movieResponse.getBody());
				movie = movieResponse.getBody();
			} else {
				logger.info("MovieInfoProxy - Some error in getting user movie rating - http status is " + movieResponse.getStatusCode());
				
				//TODO: Ideally this should not be null but some exception to indicate caller than service call has given error
				return null;
			}
		
		} catch (Exception ex) {
			
			logger.error("MovieInfoProxy - Exception occured while calling user movie rating service {} ", ex.getMessage());
			throw ex;
			
		}
		
		return movie;
		
	}
	
	private MovieBO fallbackMovieDetails(Integer movieId) {
		
		return new MovieBO(movieId, "Name not available", "No Info Exists", "N/A" );
		
		
	}
	
	
	
	
}
