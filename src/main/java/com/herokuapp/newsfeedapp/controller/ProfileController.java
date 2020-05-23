package com.herokuapp.newsfeedapp.controller;

import java.util.ArrayList;
import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.herokuapp.newsfeedapp.dto.PasswordUpdate;
import com.herokuapp.newsfeedapp.dto.UserProfile;
import com.herokuapp.newsfeedapp.entity.User;
import com.herokuapp.newsfeedapp.entity.UserDetailsImpl;
import com.herokuapp.newsfeedapp.model.Category;
import com.herokuapp.newsfeedapp.service.ProfileService;

/**
 * This controller is mapped to profile urls.
 * 
 * @since 2020-05-17
 * @author Sujan Kumar Mitra
 *
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {

	/**
	 * This member is used to update profile details
	 */
	@Autowired
	private ProfileService profileService;

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
	 * This method returns the profile information of currently logged in user.
	 * 
	 * @param model       for storing model data to render in view
	 *                    <strong>(Populated by Spring)</strong>
	 * @param userDetails currently authenticated user <strong>(Populated by
	 *                    Spring)</strong>
	 * @return view page of user profile
	 * @since 2020-05-17
	 * @author Sujan Kumar Mitra
	 */
	@GetMapping
	public String showProfile(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		UserProfile user = new UserProfile(userDetails.getUser());
		model.addAttribute("userProfile", user);

		/*
		 * used by view technology to hide or show certain fields
		 */
		model.addAttribute("isUpdatePage", false);
		model.addAttribute("buttonText", "Update");
		return "profile";
	}

	/**
	 * This method return the form to update the profile with profile data injected.
	 * 
	 * @param userDetails currently authenticated user <strong>(Populated by
	 *                    Spring)</strong>
	 * @param model       for storing model data to render in view
	 *                    <strong>(Populated by Spring)</strong>
	 * @return update form
	 * @since 2020-05-17
	 * @author Sujan Kumar Mitra
	 */
	@GetMapping("/update")
	public String showUpdate(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {

		UserProfile user = new UserProfile(userDetails.getUser());
		model.addAttribute("userProfile", user);
		model.addAttribute("categories", user.getCategories());
		ArrayList<Category> categories = new ArrayList<>(Arrays.asList(Category.values()));
		categories.remove(Category.COVID);
		model.addAttribute("categories", categories);
		/*
		 * used by view technology to hide or show certain fields
		 */
		model.addAttribute("isUpdatePage", true);
		model.addAttribute("buttonText", "Submit");
		return "profile";
	}

	/**
	 * This methods accepts the form data and calls the
	 * {@link ProfileController#profileService} to update user profile.
	 * 
	 * @param updateUser    binded form data
	 * @param bindingResult result of form binding
	 * @param model         for storing model data to render in view
	 *                      <strong>(Populated by Spring)</strong>
	 * @param userDetails   currently authenticated user <strong>(Populated by
	 *                      Spring)</strong>
	 * @return on successful redirects to home page otherwise return form with error
	 * @since 2020-05-17
	 * @author Sujan Kumar Mitra
	 */
	@PostMapping("/update")
	public String updateProfile(@Valid @ModelAttribute UserProfile updateUser, BindingResult bindingResult, Model model,
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		if (bindingResult.hasErrors()) {
			/*
			 * used by view technology to hide or show certain fields
			 */
			model.addAttribute("isUpdatePage", true);
			return "profile";
		}
		User user = new User(updateUser);
		user.setUserDetails(userDetails);
		profileService.updateProfile(user);
		return "redirect:/";
	}

	@GetMapping("/updatePassword")
	public String showPasswordUpdateForm(PasswordUpdate passwordUpdate) {
		return "password-update";
	}

	@PostMapping("/updatePassword")
	public String updatePassword(@Valid @ModelAttribute PasswordUpdate passwordUpdate, BindingResult bindingResult) {
		
		System.out.println(passwordUpdate);
		if(bindingResult.hasErrors()) {
			return "password-update";
		}
		profileService.updatePassword(passwordUpdate);
		return "redirect:/login?logout";
	}
}

