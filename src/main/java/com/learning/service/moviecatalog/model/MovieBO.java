package com.learning.service.moviecatalog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieBO {

	private Integer movieId;
	private String name;
	private String information;
	private String screenType;

}
