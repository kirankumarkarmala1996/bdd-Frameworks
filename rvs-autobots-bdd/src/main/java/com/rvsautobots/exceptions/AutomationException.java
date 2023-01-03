package com.rvsautobots.exceptions;

public class AutomationException extends Exception {
	/**
	 * @Author AutobotsBDD Cucumber Team
	 * @Description :BDD
	 */

	/**
	 * Method for throwing exception as message
	 * 
	 * @param message
	 * @throws AutomationException
	 */

	public AutomationException(String message) throws AutomationException {
		super(message);
	}

	/**
	 * Method used to return the cause of the current exception with message
	 * 
	 * @param message
	 * 
	 */
	public AutomationException(String message, Throwable cause) throws AutomationException {
		super(cause);
	}
	/**
	 * Method used to return the cause of the throwable
	 * 
	 * @param message
	 * 
	 */
	public AutomationException(Throwable cause) throws AutomationException {
		super(cause);
	}
}
