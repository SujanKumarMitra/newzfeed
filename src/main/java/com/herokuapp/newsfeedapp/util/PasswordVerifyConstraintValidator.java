package com.herokuapp.newsfeedapp.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.herokuapp.newsfeedapp.entity.UserDetailsImpl;

/**
 * This class acts as a constraint validator for {@link PasswordVerify}
 * 
 * @since 2020-05-22
 * @author Sujan Kumar Mitra
 */
@Service
public class PasswordVerifyConstraintValidator implements ConstraintValidator<PasswordVerify, String> {

	private String message;

	@Autowired
	private PasswordEncoder encoder;

	/**
	 * This method acts as a constructor to fetch the values sent by the annotation
	 * 
	 * @param constraintAnnotation the required Annotation instance
	 * @since 2020-05-22
	 * @author Sujan Kumar Mitra
	 */
	@Override
	public void initialize(PasswordVerify constraintAnnotation) {
		this.message = constraintAnnotation.message();
	}

	/**
	 * This method performs the validation.
	 * 
	 * @param value   the field on which annotation is applied
	 * @param context instance of ConstraintValidatorContext
	 * @return either valid or not
	 * @since 2020-05-22
	 * @author Sujan Kumar Mitra
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if(value == null) {
			return false;
		}
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		
		boolean matches = encoder.matches(value, userDetails.getPassword());
		/*
		 * add message to the context
		 */
		if(!matches) {
			context.buildConstraintViolationWithTemplate(message)
			.addConstraintViolation()
			.disableDefaultConstraintViolation();
		}
		return matches;

	}

}
