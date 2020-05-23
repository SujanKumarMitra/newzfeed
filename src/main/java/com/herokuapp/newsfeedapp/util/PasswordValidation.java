package com.herokuapp.newsfeedapp.util;

/**
 * This interface is used by {@link PasswordMatchesConstraintValidator} to get
 * the password values.<br>
 * All the classes which deals with password validation must implement this
 * interface and provide proper implementations.
 * 
 * @since 2020-05-22
 * @author Sujan Kumar Mitra
 *
 */
public interface PasswordValidation {
	/**
	 * @return the password
	 */
	String getPassword();

	/**
	 * @return the matching password
	 */
	String getMatchingPassword();
}
