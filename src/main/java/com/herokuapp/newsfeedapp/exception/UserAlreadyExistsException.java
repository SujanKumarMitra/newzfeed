package com.herokuapp.newsfeedapp.exception;

/**
 * This exception is thrown when an signup attempt is made with an username that
 * aleady exists in the database.
 * 
 * @since 2020-05-16
 * @author Sujan Kumar Mitra
 */
public class UserAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserAlreadyExistsException() {
		super();
	}

	public UserAlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UserAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserAlreadyExistsException(String message) {
		super(message);
	}

	public UserAlreadyExistsException(Throwable cause) {
		super(cause);
	}

}
