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

import com.learning.service.moviecatalog.model.UserMovieRatingBO;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
public class UserMovieRatingServiceProxy {
	
	Logger logger = LoggerFactory.getLogger(UserMovieRatingServiceProxy.class);
	
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${movie_catalog.user_movie_rating_service.url}")
	private String movieRatingServiceUrl;
	
	@HystrixCommand(fallbackMethod = "getFallbackMovieRatings")
	public UserMovieRatingBO getMovieRatings(String userId) {
		
		UserMovieRatingBO userMovieRating = null;
		
		logger.info("UserMovieRatingServiceProxy - Received movie rating request for {}" + userId);
		
		try {
			
			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(movieRatingServiceUrl);
			
			builder.queryParam("userId", userId);
			
			logger.info("UserMovieRatingServiceProxy - request being made at  {}" + builder.toUriString() );
			
			
			ResponseEntity<UserMovieRatingBO> userMovieRatingResponse = restTemplate.getForEntity(builder.toUriString(), UserMovieRatingBO.class);
			
			if (userMovieRatingResponse.getStatusCode()== HttpStatus.OK) {
				logger.info("UserMovieRatingServiceProxy - Got the user movie ratings successfully {} " + userMovieRatingResponse.getBody());
				userMovieRating = userMovieRatingResponse.getBody();
			} else {
				//TODO: Ideally this should not be null but some exception to indicate caller than service call has given error
				logger.info("UserMovieRatingServiceProxy - Some error in getting user movie rating - http status is " + userMovieRatingResponse.getStatusCode());
				
				return null;
			}
			
			
		
		} catch (Exception ex) {
			
			logger.error("UserMovieRatingServiceProxy - Exception occured while calling user movie rating service {} ", ex.getMessage());
			throw ex;
			
		}
		
		return userMovieRating;
		
	}
	
	private UserMovieRatingBO getFallbackMovieRatings(String userId) {
		
		return null;
		
	}
	
}
