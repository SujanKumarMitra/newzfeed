package com.herokuapp.newsfeedapp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.herokuapp.newsfeedapp.model.Article;

/**
 * This class deals with headline news articles
 * 
 * @since 2020-04-30
 * @author Sujan Kumar Mitra
 *
 */
@Service
public class HeadlineService {

	/**
	 * This member is used to fetch the news map.
	 * 
	 * @since 2020-04-30
	 * @author Sujan Kumar Mitra
	 * @see NewsService
	 */
	@Autowired
	private NewsService newsService;

	/**
	 * This member holds number of articles to return per page.
	 * 
	 * @since 2020-04-30
	 * @author Sujan Kumar Mitra
	 */
	@Value("${articles.length}")
	private int pageSize;

	/**
	 * This member holds the headline articles.
	 * 
	 * @since 2020-04-30
	 * @author Sujan Kumar Mitra
	 */
	private List<Article> articles= new ArrayList<Article>();

	/**
	 * This member is for for logging purposes.
	 * 
	 * @since 2020-04-30
	 */
	private Logger logger = Logger.getLogger(getClass().getName());

	/**
	 * This method is invoked when Spring Container has created the instance of this
	 * class.<br>
	 * Uses the newsService to fetch all the headline articles and store it in
	 * {@link HeadlineService#articles}
	 * 
	 * @since 2020-04-30
	 * @author Sujan Kumar Mitra
	 */
	@PostConstruct
	private void init() {
		logger.info("Fetching headline articles");

		Collection<List<Article>> values = newsService.getNewsMap().values();
		List<Article> articles = new ArrayList<>();
		/*
		 * Adds all the articles fetched from news map to a new list. Converting 2-D
		 * list to 1-D list
		 */
		values.forEach(articles::addAll);

		/* arranges the articles in a random order. */
		Collections.shuffle(articles);
		logger.info("Headlines fetched");
		setArticles(articles);
	}

	/**
	 * Sets the articles fetched from news map to {@link HeadlineService#articles}
	 * 
	 * @param articles the articles to add
	 * @since 2020-04-30
	 * @author Sujan Kumar Mitra
	 */
	public void setArticles(List<Article> articles) {
		this.articles = articles;
		logger.info("Total articles fetched := " + articles.size());
	}

	/**
	 * This method returns the list of articles based on page provided
	 * 
	 * @param page requested page
	 * @return list of articles of size {@link HeadlineService#pageSize}
	 * @since 2020-04-30
	 * @author Sujan Kumar Mitra
	 */
	public List<Article> getHeadlines(int page) {

		if (articles.size() < page * pageSize) {
			return null;
		} else if (articles.size() <= page * pageSize + pageSize) {
			return articles.subList(page * pageSize, articles.size());
		} else {
			return articles.subList(page * pageSize, page * pageSize + pageSize);
		}
	}

}
