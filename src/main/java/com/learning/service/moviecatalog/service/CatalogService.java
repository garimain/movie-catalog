package com.learning.service.moviecatalog.service;



import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import com.learning.service.moviecatalog.model.CatalogSearchBO;
import com.learning.service.moviecatalog.model.MovieBO;
import com.learning.service.moviecatalog.model.MovieCatalog;
import com.learning.service.moviecatalog.model.MovieRatingDetails;
import com.learning.service.moviecatalog.model.UserMovieRatingBO;
import com.learning.service.moviecatalog.service.proxy.MovieInfoProxy;
import com.learning.service.moviecatalog.service.proxy.UserMovieRatingServiceProxy;

@Service
@RefreshScope
public class CatalogService {
	
	Logger logger = LoggerFactory.getLogger(CatalogService.class);
	
	@Autowired
	private UserMovieRatingServiceProxy userMovieRatingProxy;
	
	@Autowired
	private MovieInfoProxy movieInfoProxy;
	
	
	public MovieCatalog getMovieCatalog(CatalogSearchBO catalogSearchBO) {
		
		List<MovieRatingDetails> movieRatingDetails  = null;
		MovieCatalog movieCatalog = new MovieCatalog();
		String userId = catalogSearchBO.getUserId();
		movieCatalog.setUserId(userId);
		
		logger.info("CatalogService - Received request for user {}", userId);
		
		
		
		//Step 1 - invoke rating service to get the movie id and ratings for a given user id
		UserMovieRatingBO userMovieRating = userMovieRatingProxy.getMovieRatings(userId);
		
		//replace this with optional
		
		if (userMovieRating != null && userMovieRating.getListMovie() != null) {
			
			movieRatingDetails = getMovieRatingDetails(userMovieRating);
			
			movieCatalog.setMovieRatingDetails(movieRatingDetails);
		}
		
		logger.info("Response processed for user {}", userId);
		
		return movieCatalog;
		
	}
	
	private List<MovieRatingDetails> getMovieRatingDetails(UserMovieRatingBO userMovieRating) {
		
		return userMovieRating.getListMovie().stream().map(mr-> {
			MovieBO movie = movieInfoProxy.getMovieDetails(mr.getMovieId());
			MovieRatingDetails movieRatingDetail = new MovieRatingDetails(movie.getName(), movie.getInformation(), mr.getRating());
			return movieRatingDetail;
		
		}).collect(Collectors.toList());
		
	}
	
	
}
