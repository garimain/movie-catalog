package com.learning.service.moviecatalog.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserMovieRatingBO {
	
	private String userId;
	
	
	private List<MovieRatingBO> listMovie;

	
	

}
