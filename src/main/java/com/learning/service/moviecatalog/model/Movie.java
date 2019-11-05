package com.learning.service.moviecatalog.model;

public class Movie {
	
	private String movieId;
	private String name;
	private String information;
	
	public Movie() {
		
	}
	
	public Movie(String movieId, String name, String information) {
		super();
		this.movieId=movieId;
		this.name = name;
		this.information = information;
	}
	
	public String getMovieId() {
		return movieId;
	}
	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	
	
	
	

}
