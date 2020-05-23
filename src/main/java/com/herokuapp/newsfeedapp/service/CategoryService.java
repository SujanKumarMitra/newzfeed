package com.herokuapp.newsfeedapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.herokuapp.newsfeedapp.controller.CategoryController;
import com.herokuapp.newsfeedapp.exception.CategoryNotFoundException;
import com.herokuapp.newsfeedapp.exception.NewsNotFoundException;
import com.herokuapp.newsfeedapp.model.Article;
import com.herokuapp.newsfeedapp.model.Category;

/**
 * This class deals with operations related to news article categories.
 * 
 * @since 2020-05-11
 * @author Sujan Kumar Mitra
 * @see CategoryController
 *
 */
@Service
public class CategoryService {

	/**
	 * This member is used to fetch the news map
	 * 
	 * @since 2020-05-11
	 * @author Sujan Kumar Mitra
	 * @see NewsService
	 */
	@Autowired
	private NewsService newsService;

	/**
	 * This member is used to fetch news articles by passing search query
	 * 
	 * @since 2020-05-11
	 * @author Sujan Kumar Mitra
	 * @see SearchService
	 */
	@Autowired
	private SearchService searchService;

	/**
	 * This member holds number of articles to return per page.
	 * 
	 * @since 2020-05-11
	 * @author Sujan Kumar Mitra
	 */
	@Value("${articles.length}")
	private int pageSize;

	/**
	 * This method returns list of articles based on category and page provided
	 * 
	 * @param category articles of a particular category
	 * @param page     requested page number
	 * @return list of articles of size {@link CategoryService#pageSize}
	 * @throws NewsNotFoundException     might throw this if articles related to
	 *                                   this category not found
	 * @throws CategoryNotFoundException will throw this if unknown category is
	 *                                   passed
	 * @since 2020-05-11
	 * @author Sujan Kumar Mitra
	 */
	public List<Article> getArticlesFromCategory(Category category, int page)
			throws NewsNotFoundException, CategoryNotFoundException {

		/* If category is covid then delegate to searchService */
		if (category == Category.COVID) {
			return searchService.getArticles(category.getCategory(), page);
		}

		List<Article> articles = newsService.getNewsMap().getOrDefault(category, null);
		if (articles == null) {
			throw new CategoryNotFoundException("News with category := " + category.getCategory() + " not found");
		}

		if (articles.size() < page * pageSize) {
			return null;
		} else if (articles.size() <= page * pageSize + pageSize) {
			return articles.subList(page * pageSize, articles.size());
		} else {
			return articles.subList(page * pageSize, page * pageSize + pageSize);
		}

	}

}
