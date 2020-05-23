package com.herokuapp.newsfeedapp.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * This annotation is used to verify if the password user gave is correct or not.
 * or not (acts as a constraint validator).
 * 
 * @since 2020-05-22
 * @author Sujan Kumar Mitra
 * @see PasswordVerifyConstraintValidator
 */

@Constraint(validatedBy = PasswordVerifyConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PasswordVerify {

	/**
	 * This field is the message which is displayed to user in case of validation
	 * failure. Defaults to <strong>Do not match</strong>
	 * 
	 * @return the error message to display
	 */
	public String message() default "Password not same";

	public Class<?>[] groups() default {};

	public Class<? extends Payload>[] payload() default {};

}
