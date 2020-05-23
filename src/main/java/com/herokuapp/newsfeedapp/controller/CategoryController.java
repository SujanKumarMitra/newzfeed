package com.herokuapp.newsfeedapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.herokuapp.newsfeedapp.entity.UserDetailsImpl;
import com.herokuapp.newsfeedapp.exception.CategoryNotFoundException;
import com.herokuapp.newsfeedapp.exception.InvalidParameterException;
import com.herokuapp.newsfeedapp.exception.NewsNotFoundException;
import com.herokuapp.newsfeedapp.model.Article;
import com.herokuapp.newsfeedapp.model.Category;
import com.herokuapp.newsfeedapp.service.CategoryService;

/**
 * This class is used to map category url patterns.
 * 
 * @since 2020-05-11
 * @author Sujan Kumar Mitra
 */
@Controller
@RequestMapping("/category")
public class CategoryController {

	/**
	 * Service class for providing articles of a particular category.
	 * 
	 * @see CategoryService
	 * 
	 */
	@Autowired
	private CategoryService categoryService;

	/**
	 * This method is returns the view pages of news of different categories.
	 * 
	 * @param categoryId id of the category
	 * @param page       requested page
	 * @param model      for putting model data, <strong>(populated by Spring
	 *                   Container)</strong>
	 * @param request    http request information <strong>(populated by Spring
	 *                   Container)</strong>
	 * @param user       if user is logged in then, instance of UserDetails
	 *                   otherwise <strong>null</strong> <strong>(populated by
	 *                   Spring Container)</strong>
	 * @return view page with data
	 * @throws NewsNotFoundException     might throw this, if news articles of the
	 *                                   requested category not found
	 * @throws CategoryNotFoundException will throw this, if category of unknown
	 *                                   name is submitted
	 * @throws InvalidParameterException will throw this if negative or zero page
	 *                                   number is passed
	 * @since 2020-05-11
	 * @author Sujan Kumar Mitra
	 */
	@GetMapping("/{category}")
	public String getArticlesofCategory(@PathVariable(value = "category") String categoryId,
			@RequestParam(name = "page", defaultValue = "1") int page, Model model, HttpServletRequest request,
			@AuthenticationPrincipal UserDetailsImpl user)
			throws NewsNotFoundException, CategoryNotFoundException, InvalidParameterException {

		if (page <= 0) {
			throw new InvalidParameterException("Page " + page + " is not a valid parameter");
		}
		Category category;
		if (categoryId.toLowerCase().contains("covid")) {
			category = Category.COVID;
		} else {
			try {
				category = Category.valueOf(categoryId.toUpperCase());
			} catch (IllegalArgumentException exception) {
				throw new CategoryNotFoundException("Category := " + categoryId + " not found");
			}
		}

		List<Article> articles = categoryService.getArticlesFromCategory(category, page);

		if (user != null) {
			model.addAttribute("user", user.getUser());
		}

		model.addAttribute("articles", articles);
		model.addAttribute("category", category.getCategory());
		model.addAttribute("page", page);
		model.addAttribute("heading", "Displaying news of "+category+" category");
		model.addAttribute("linkPrevious", request.getRequestURI() + "?page=" + (page - 1));
		model.addAttribute("linkNext", request.getRequestURI() + "?page=" + (page + 1));
		return "newsfeed";

	}

}
