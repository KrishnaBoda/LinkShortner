package com.pro.exceptions;

public class InSufficientInputException extends Exception{
	
	private static final long serialVersionUID = 7842170097518321488L;

	/**
	 * Constructs a InSufficientInputException using the given exception message.
	 *
	 * @param message The message explaining the reason for the exception
	 */
	public InSufficientInputException(String description) {
		super(description);
	}

}
