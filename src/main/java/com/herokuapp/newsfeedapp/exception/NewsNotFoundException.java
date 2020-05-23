package com.herokuapp.newsfeedapp.exception;

/**
 * This exception is thrown when news articles are not found.
 * 
 * @since 2020-04-30
 * @author Sujan Kumar Mitra
 */
public class NewsNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public NewsNotFoundException() {
		super();
	}

	public NewsNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NewsNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NewsNotFoundException(String message) {
		super(message);
	}

	public NewsNotFoundException(Throwable cause) {
		super(cause);
	}

}
