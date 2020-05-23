package com.herokuapp.newsfeedapp.exception;

/**
 * This exception is throw when requested category is not found
 * 
 * @since 2020-05-11
 * @author Sujan Kumar Mitra
 */
public class CategoryNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CategoryNotFoundException() {
		super();
	}

	public CategoryNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CategoryNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public CategoryNotFoundException(String message) {
		super(message);
	}

	public CategoryNotFoundException(Throwable cause) {
		super(cause);
	}

}
