package com.learning.service.moviecatalog.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value = "MovieCatalog Model class")
public class MovieCatalog {
	
	@ApiModelProperty(name = "userId", dataType = "String", value = "user id of the catalog user")
	private String userId;
	
	@ApiModelProperty(name = "userName", dataType = "String", value= "user name of the catalog user")
	private String userName;
	
	private List<MovieRatingDetails> movieRatingDetails;
	
	
	public MovieCatalog() {
		
	}

	public MovieCatalog(String userId, String userName, List<MovieRatingDetails> movieRatingDetails) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.movieRatingDetails = movieRatingDetails;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<MovieRatingDetails> getMovieRatingDetails() {
		return movieRatingDetails;
	}

	public void setMovieRatingDetails(List<MovieRatingDetails> movieRatingDetails) {
		this.movieRatingDetails = movieRatingDetails;
	}
	
	
	
	
	

}
