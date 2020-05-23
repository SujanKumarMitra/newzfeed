package com.herokuapp.newsfeedapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.herokuapp.newsfeedapp.entity.UserDetailsImpl;
import com.herokuapp.newsfeedapp.exception.InvalidParameterException;
import com.herokuapp.newsfeedapp.exception.NewsNotFoundException;
import com.herokuapp.newsfeedapp.model.Article;
import com.herokuapp.newsfeedapp.service.SearchService;

/**
 * This class is mapped to seach url pattern.
 * 
 * @since 2020-04-30
 * @author Sujan Kumar Mitra
 */
@Controller
public class SearchController {

	/**
	 * Service class for performing search queries.
	 * 
	 * @see SearchService
	 */
	@Autowired
	private SearchService searchService;

	/**
	 * @param model   for putting model data <strong>(populated by Spring
	 *                Container)</strong>
	 * @param query   query string
	 * @param page    requested page defaults to 1
	 * @param request http request information <strong>(populated by Spring
	 *                Container)</strong>
	 * @param user    if user is logged in then, instance of UserDetails otherwise
	 *                <strong>null</strong> <strong>(populated by Spring
	 *                Container)</strong>
	 * @return view ppage with model data <strong>(populated by Spring
	 *         Container)</strong>
	 * @throws NewsNotFoundException                   might throw this if news
	 *                                                 articles related to query not
	 *                                                 found
	 * @throws MissingServletRequestParameterException will throw this if query
	 *                                                 string is missing
	 * @throws InvalidParameterException               will throw this if negative
	 *                                                 or zero page number is passed
	 * @since 2020-04-30
	 * @author Sujan Kumar Mitra
	 */
	@GetMapping("/search")
	public String searchNews(Model model, @RequestParam(name = "query", required = true) String query,
			@RequestParam(name = "page", defaultValue = "1") int page, HttpServletRequest request,
			@AuthenticationPrincipal UserDetailsImpl user)
			throws NewsNotFoundException, MissingServletRequestParameterException, InvalidParameterException {

		if (page <= 0) {
			throw new InvalidParameterException("Page " + page + " is not a valid parameter");
		}
		if (query.equals("")) {
			throw new MissingServletRequestParameterException(query, "String");
		}
		List<Article> articles = searchService.getArticles(query, page);

		if (user != null) {
			model.addAttribute("user", user.getUser());
		}
		model.addAttribute("page", page);
		model.addAttribute("linkPrevious", request.getRequestURI() + "?query=" + query + "&page=" + (page - 1));
		model.addAttribute("linkNext", request.getRequestURI() + "?query=" + query + "&page=" + (page + 1));
		model.addAttribute("articles", articles);
		return "newsfeed";
	}
}
