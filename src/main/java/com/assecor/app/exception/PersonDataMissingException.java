package com.assecor.app.exception;

/**
 * Custom exception for when person's data is incomplete
 */
public class PersonDataMissingException extends RuntimeException
{
	public PersonDataMissingException()
	{
		super("Cannot create person, some data is missing!");
	}
}
