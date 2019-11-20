package com.learning.service.moviecatalog.service.proxy;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.learning.service.moviecatalog.model.MovieRating;
import com.learning.service.moviecatalog.model.UserMovieRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
public class UserMovieRatingServiceProxy {
	
	Logger logger = LoggerFactory.getLogger(UserMovieRatingServiceProxy.class);
	
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${movie_catalog.user_movie_rating_service.url}")
	private String movieRatingServiceUrl;
	
	@HystrixCommand(fallbackMethod = "getFallbackMovieRatings")
	public UserMovieRating getMovieRatings(String userId) {
		
		UserMovieRating userMovieRating = null;
		
		
		try {
			
			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(movieRatingServiceUrl);
			
			builder.queryParam("userId", userId);
		
			ResponseEntity<UserMovieRating> userMovieRatingResponse = restTemplate.getForEntity(builder.toUriString(), UserMovieRating.class);
			
			if (userMovieRatingResponse.getStatusCode()== HttpStatus.OK) {
				logger.info("Got the user movie ratings successfully");
				userMovieRating = userMovieRatingResponse.getBody();
			} else {
				//TODO: Ideally this should not be null but some exception to indicate caller than service call has given error
				return null;
			}
			
			
		
		} catch (Exception ex) {
			
			logger.error("Exception occured while calling user movie rating service {} ", ex.getMessage());
			throw ex;
			
		}
		
		return userMovieRating;
		
	}
	
	private UserMovieRating getFallbackMovieRatings(String userId) {
		
		List<MovieRating> movieRatings =  Arrays.asList(new MovieRating(userId, null, 0.0));
		return new UserMovieRating(userId, movieRatings);
		
	}
	
}
