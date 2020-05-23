package com.herokuapp.newsfeedapp.exception;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.herokuapp.newsfeedapp.entity.UserDetailsImpl;

/**
 * This class handles all the exception that are thrown and not handled by the
 * Controllers.
 * 
 * @since 2020-04-30
 * @author Sujan Kumar Mitra
 *
 */
@ControllerAdvice
public class GenericWebApplicationExceptionHandler {

	/**
	 * This method handles the exception when a url does not contain a required
	 * parameter
	 * 
	 * @param exception   exception which is thrown
	 * @param model       model for putting data model <strong>(populated by Spring
	 *                    Container)</strong>
	 * @param userDetails currently authenticated user <strong>(Populated by
	 *                    Spring)</strong>
	 * @return error page with error information
	 * @since 2020-05-11
	 * @author Sujan Kumar Mitra
	 * 
	 */
	@ExceptionHandler
	public String parametersNotPassed(MissingServletRequestParameterException exception, Model model,
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		model.addAttribute("error", exception.getMessage());
		if (userDetails != null) {
			model.addAttribute("user", userDetails.getUser());
		}
		return "error";

	}

	/**
	 * This exception handles the exception when a requested category is not found.
	 * 
	 * @param exception   exception which is thrown
	 * @param model       model object for putting model data <strong>(populated by
	 *                    Spring Container)</strong>
	 * @param userDetails currently authenticated user <strong>(Populated by
	 *                    Spring)</strong>
	 * @return error page with error information
	 * @since 2020-05-11
	 * @author Sujan Kumar Mitra
	 */
	@ExceptionHandler
	public String categoryNotFound(CategoryNotFoundException exception, Model model,
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		model.addAttribute("error", exception.getMessage());
		if (userDetails != null) {
			model.addAttribute("user", userDetails.getUser());
		}
		return "error";
	}

	/**
	 * This method handles the exception when invalid parameters are passed in the
	 * query.
	 * 
	 * @param exception   which is thrown
	 * @param model       object for putting model data <strong>(Populated by
	 *                    Spring)</strong>
	 * @param userDetails currently authenticated user <strong>(Populated by
	 *                    Spring)</strong>
	 * @return error page with error information
	 */
	@ExceptionHandler
	public String invalidParameter(InvalidParameterException exception, Model model,
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		model.addAttribute("error", exception.getMessage());
		if (userDetails != null) {
			model.addAttribute("user", userDetails.getUser());
		}
		return "error";
	}

}
