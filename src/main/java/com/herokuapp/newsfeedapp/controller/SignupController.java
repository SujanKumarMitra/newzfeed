package com.herokuapp.newsfeedapp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.herokuapp.newsfeedapp.dto.SignupUser;
import com.herokuapp.newsfeedapp.exception.UserAlreadyExistsException;
import com.herokuapp.newsfeedapp.service.SignupService;

/**
 * This controller deals with Signup activities. Contains method of showing
 * signup-form and processing signup
 * 
 * @since 2020-05-15
 * @author Sujan Kumar Mitra
 */
@Controller
public class SignupController {

	/**
	 * Service class for performing signup activities
	 * 
	 * @see SignupService
	 */
	@Autowired
	private SignupService signupService;

	/**
	 * This method registers StringTrimmer in form. When a form is submitted, it
	 * trims all String type input fields
	 * 
	 * @param webDataBinder instance of {@link WebDataBinder} <strong>(Populated by
	 *                      Spring)</strong>
	 * @since 2020-05-15
	 * @author Sujan Kumar Mitra
	 */
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	/**
	 * This method returns the view of signup page
	 * 
	 * @param signupUser for form bindings (Populated by Spring)
	 * @return signup form
	 * @since 2020-05-15
	 * @author Sujan Kumar Mitra
	 */
	@GetMapping("/signup")
	public String showSignup(SignupUser signupUser) {
		return "signup";
	}

	/**
	 * This method processes the signup submission.
	 * 
	 * @param signupUser    SignupUser data submitted in the form <strong>(populated
	 *                      by Spring Container)</strong>
	 * @param bindingResult contains error related to form binding
	 * @param model         model object for putting model data <strong>(populated
	 *                      by Spring Container)</strong>
	 * @return on success login page, on error previous form with error attributes
	 * @throws UserAlreadyExistsException will throw this if user with email already
	 *                                    exists.
	 * @since 2020-05-15
	 * @author Sujan Kumar Mitra
	 */
	@PostMapping("/signup")
	public String signup(@Valid @ModelAttribute SignupUser signupUser, BindingResult bindingResult, Model model)
			throws UserAlreadyExistsException {

		try {
			if (bindingResult.hasErrors()) {
				return "signup";
			}
			signupService.signup(signupUser);
			return "redirect:/login";
		} catch (UserAlreadyExistsException exception) {
			model.addAttribute("signupUser", signupUser);
			model.addAttribute("error", "Account already exists with this email.");
			return "signup";
		}
	}
}
