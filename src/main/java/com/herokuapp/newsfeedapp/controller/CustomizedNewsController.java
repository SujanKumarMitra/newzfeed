package com.herokuapp.newsfeedapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.herokuapp.newsfeedapp.entity.UserDetailsImpl;
import com.herokuapp.newsfeedapp.exception.InvalidParameterException;
import com.herokuapp.newsfeedapp.model.Article;
import com.herokuapp.newsfeedapp.service.CustomizedNewsService;

/**
 * this controller is used to map custom news articles list
 * 
 * @author Sujan Kumar Mitra
 *
 */
@Controller
public class CustomizedNewsController {

	/**
	 * to retrieve {@link CustomizedNewsService} bean
	 */
	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * This method retrieves currently logged in user details, fetches news of
	 * categories the user is subscribed to.
	 * 
	 * @param page        requested page
	 * @param model       for putting model data <strong>(populated by Spring
	 *                    Container)</strong>
	 * @param userDetails currently logged in user <strong>(populated by Spring
	 *                    Container)</strong>
	 * @param request     http request information <strong>(populated by Spring
	 *                    Container)</strong>
	 * @return view with articles.
	 * @throws InvalidParameterException will throw if invalid page number is passed
	 */
	@GetMapping("/custom")
	public String getArticles(@RequestParam(name = "page", defaultValue = "1") int page, Model model,
			@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletRequest request)
			throws InvalidParameterException {
		if (page <= 0) {
			throw new InvalidParameterException("Page " + page + " is not a valid parameter");
		}

		if (userDetails != null) {
			model.addAttribute("user", userDetails.getUser());
		}
		CustomizedNewsService newsService = applicationContext.getBean(CustomizedNewsService.class);
		if (newsService.isUserNotSubscribedToAnyCategory()) {
			model.addAttribute("error", true);
			model.addAttribute("message",
					"You have not subscribed to any categories yet." + "Go to profile and add categories");
		} else {
			List<Article> articles = newsService.getArticles(page);
			model.addAttribute("articles", articles);
		}

		model.addAttribute("page", page);
		model.addAttribute("category", "for you");
		model.addAttribute("heading", "Displaying News Articles from your subscribed list");
		model.addAttribute("linkPrevious", request.getRequestURI() + "?page=" + (page - 1));
		model.addAttribute("linkNext", request.getRequestURI() + "?page=" + (page + 1));
		return "newsfeed";
	}

}
