package com.herokuapp.newsfeedapp.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Service;

/**
 * This class acts as a constraint validator for {@link PasswordMatches}
 * 
 * @since 2020-05-16
 * @author Sujan Kumar Mitra
 */
@Service
public class PasswordMatchesConstraintValidator implements ConstraintValidator<PasswordMatches, PasswordValidation> {

	private String message;
	private String fieldName;

	/**
	 * This method acts as a constructor to fetch the values sent by the annotation
	 * 
	 * @param constraintAnnotation the required Annotation instance
	 * @since 2020-05-16
	 * @author Sujan Kumar Mitra
	 */
	@Override
	public void initialize(PasswordMatches constraintAnnotation) {
		this.message = constraintAnnotation.message();
		this.fieldName = constraintAnnotation.fieldName();
	}

	/**
	 * This method performs the validation.
	 * 
	 * @param value   the field on which annotation is applied
	 * @param context instance of ConstraintValidatorContext
	 * @return either valid or not
	 * @since 2020-05-16
	 * @author Sujan Kumar Mitra
	 */
	@Override
	public boolean isValid(PasswordValidation value, ConstraintValidatorContext context) {
		if (value.getPassword() == null || value.getMatchingPassword() == null) {
			return false;
		}
		boolean equals = value.getPassword().equals(value.getMatchingPassword());
		/*
		 * Binds the constraint error to the field
		 */
		if (!equals) {
			context.buildConstraintViolationWithTemplate(message)
					.addPropertyNode(fieldName)
					.addConstraintViolation()
					.disableDefaultConstraintViolation();
		}
		return equals;
	}

}
