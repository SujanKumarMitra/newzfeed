package com.herokuapp.newsfeedapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.herokuapp.newsfeedapp.entity.UserDetailsImpl;
import com.herokuapp.newsfeedapp.exception.InvalidParameterException;
import com.herokuapp.newsfeedapp.model.Article;
import com.herokuapp.newsfeedapp.service.HeadlineService;

/**
 * This controller is mapped to the headlines and about.
 * 
 * @since 2020-04-30
 * @author Sujan Kumar Mitra
 */
@Controller
public class NewsController {

	/**
	 * Service class for fetching headline articles.
	 * 
	 * @see HeadlineService
	 */
	@Autowired
	private HeadlineService headLineService;

	/**
	 * This method return the headlines in view
	 * 
	 * @param model       model instance for putting model data <strong>(populated
	 *                    by Spring Container)</strong>
	 * @param page        requested page
	 * @param request     http request information <strong>(populated by Spring
	 *                    Container)</strong>
	 * @param userDetails if user is logged in then, instance of UserDetails
	 *                    otherwise <strong>null</strong> <strong>(populated by
	 *                    Spring Container)</strong>
	 *
	 * @throws InvalidParameterException will throw this if negative or zero page
	 *                                   number is passed
	 * @return view page with model data
	 * @since 2020-04-30
	 * @author Sujan Kumar Mitra
	 */
	@RequestMapping(path = { "/headlines", "/" })
	public String getHeadlines(Model model, @RequestParam(name = "page", defaultValue = "1") int page,
			HttpServletRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails)
			throws InvalidParameterException {

		if (page <= 0) {
			throw new InvalidParameterException("Page " + page + " is not a valid parameter");
		}
		if (userDetails != null) {
			model.addAttribute("user", userDetails.getUser());
		}
		boolean isLoggedIn = request.getAttribute("isLoggedIn") == null ? false : true;
		if (isLoggedIn) {
			model.addAttribute("warning", isLoggedIn);
			model.addAttribute("message", "You are already logged in! Please logout first to login again");
		}
		List<Article> headlines = headLineService.getHeadlines(page - 1);
		model.addAttribute("articles", headlines);
		model.addAttribute("page", page);
		model.addAttribute("heading", "Today's Headlines");
		model.addAttribute("linkPrevious", request.getRequestURI() + "?page=" + (page - 1));
		model.addAttribute("linkNext", request.getRequestURI() + "?page=" + (page + 1));
		return "newsfeed";

	}

	/**
	 * This method returns the view page of about-us
	 * 
	 * @param userDetails if user is logged in then, instance of UserDetails
	 *                    otherwise <strong>null</strong> <strong>(populated by
	 *                    Spring Container)</strong>
	 * 
	 * @param model       model instance for putting model data <strong>(populated
	 *                    by Spring Container)</strong>
	 * @return view page with model data
	 * @since 2020-05-21
	 * @author Sujan Kumar Mitra
	 */
	@GetMapping("/about")
	public String aboutUs(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
		if (userDetails != null) {
			model.addAttribute("user", userDetails.getUser());
		}
		return "about-us";
	}

}
