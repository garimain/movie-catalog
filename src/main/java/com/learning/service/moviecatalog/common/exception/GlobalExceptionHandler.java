package com.learning.service.moviecatalog.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.learning.service.moviecatalog.model.MovieCatalogError;


@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(CatalogSearchMissingMandatoryFieldsException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    public MovieCatalogError handleMissingFieldException(Exception ex, WebRequest request) {
		
		MovieCatalogError error;
		if (ex instanceof CatalogSearchMissingMandatoryFieldsException) {
			error = new MovieCatalogError("MissingMandatoryFields", ex.getMessage());
			
		} else {
			error = new MovieCatalogError("MissingMandatoryFields", ex.getMessage());
		}
		
		return error;
		
	}
	
	
	
	
}
