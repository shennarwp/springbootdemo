package com.shennarwp.app.exception;

/**
 * Exception class for when the person with passed id is not found in the repository
 */
public class PersonNotFoundException extends RuntimeException
{
	public PersonNotFoundException(Long id)
	{
		super("Person with this id: " + id + " is not found!");
	}
}
