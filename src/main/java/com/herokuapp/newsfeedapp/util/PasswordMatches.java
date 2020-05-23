package com.herokuapp.newsfeedapp.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * This annotation is used to see if the password and confirm password matches
 * or not (acts as a constraint validator).
 * 
 * @since 2020-05-15
 * @author Sujan Kumar Mitra
 * @see PasswordMatchesConstraintValidator
 */

@Constraint(validatedBy = PasswordMatchesConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PasswordMatches {

	/**
	 * This field is for binding the error to a specific field. Defaults to
	 * <strong>password</strong>.
	 * 
	 * @return the fieldName
	 */
	public String fieldName() default "password";

	/**
	 * This field is the message which is displayed to user in case of validation
	 * failure. Defaults to <strong>Do not match</strong>
	 * 
	 * @return the error message to display
	 */
	public String message() default "Do not match";

	public Class<?>[] groups() default {};

	public Class<? extends Payload>[] payload() default {};

}
