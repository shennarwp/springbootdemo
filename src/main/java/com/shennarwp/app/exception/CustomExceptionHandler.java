package com.shennarwp.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception handler for RESTController
 */
@ControllerAdvice
public class CustomExceptionHandler
{
	/* for POST save new person, when the data is incomplete */
	@ResponseBody
	@ExceptionHandler(PersonDataMissingException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String personDataMissingHandler(PersonDataMissingException ex) {
		return ex.getMessage();
	}

	/* for GET return one person, when the id does not exist */
	@ResponseBody
	@ExceptionHandler(PersonNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String personNotFoundHandler(PersonNotFoundException ex) {
		return ex.getMessage();
	}
}
