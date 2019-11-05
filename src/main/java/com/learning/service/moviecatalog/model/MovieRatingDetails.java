package com.learning.service.moviecatalog.model;

public class MovieRatingDetails {
	
	
	private String name;
	private String information;
	private double rating;
	
	
	
	public MovieRatingDetails(String name, String information, double rating) {
		super();
		this.name = name;
		this.information = information;
		this.rating = rating;
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
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	
	

}
