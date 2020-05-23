package com.herokuapp.newsfeedapp.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.herokuapp.newsfeedapp.exception.NewsNotFoundException;
import com.herokuapp.newsfeedapp.model.Article;
import com.herokuapp.newsfeedapp.model.Category;
import com.herokuapp.newsfeedapp.model.News;

/**
 * This is the main class which fetches the news articles from the api.
 * 
 * @since 2020-04-30
 * @author Sujan Kumar Mitra
 * @see NewsService#init()
 */
@Service
public class NewsService {

	/**
	 * This member holds the list of news articles based on category.
	 * 
	 */
	private Map<Category, List<Article>> newsMap = new LinkedHashMap<>();
	/**
	 * for logging purposes
	 */
	private Logger logger = Logger.getLogger(getClass().getName());

	/**
	 * List of categories to fetch news of.
	 */
	@Value("${headlines.categories}")
	private List<Category> categories;

	/**
	 * Base url of news provider api.
	 */
	@Value("${api.url.headlines}")
	private String url;

	/**
	 * The secret token of news api provider
	 */
	@Value("${api.key}")
	private String apiKey;

	/**
	 * This member helps in de-serializing the response provided by the api.
	 */
	@Autowired
	private ObjectMapper mapper;

	/**
	 * This member is used for RESTful communications.
	 */
	@Autowired
	private RestTemplate template;

	/**
	 * for getting {@link Thread} instances
	 */
	@Autowired
	private ThreadPoolService threadPoolService;

	/**
	 * This method return the news map.
	 * if updation process is going on, then goes in {@link Object#wait} is called.
	 * 
	 * @return the news map
	 * @since 2020-04-30
	 * @author Sujan Kumar Mitra
	 */
	public synchronized Map<Category, List<Article>> getNewsMap() {
		if (!threadPoolService.areAllThreadsDead()) {
			try {
				wait();
			} catch (Exception e) {
				logger.warning("Exception caught from waiting state.");
				logger.warning(e.getLocalizedMessage());
			}
		}
		return newsMap;
	}

	/**
	 * This method returns the list of categories, it has fetched the news of.
	 * 
	 * @return list of categories
	 * @since 2020-04-30
	 * @author Sujan Kumar Mitra
	 */
	public List<Category> getCategories() {
		return categories;
	}

	/**
	 * This method returns the list of articles of a particular category.
	 * 
	 * @param category articles of category it fetches
	 * @return list of articles it has fetched
	 * @throws NewsNotFoundException might throw this if articles of the category
	 *                               passed is not found.
	 * @since 2020-04-30
	 * @author Sujan Kumar Mitra
	 */
	private List<Article> getArticlesFromCategory(Category category) throws NewsNotFoundException {
		String callableUrl = url.concat("&category=" + category.getCategory());
		logger.info("Getting news of " + category);
		ResponseEntity<String> responseEntity = template.exchange(callableUrl, HttpMethod.GET, null, String.class);
		News news = null;
		try {
			if (responseEntity.getStatusCode().value() == 200) {
				news = mapper.readValue(responseEntity.getBody(), News.class);
				logger.info("Successfully got news of " + category);
			}
		} catch (Exception e) {
			throw new NewsNotFoundException(e);
		}
		return news.getArticles();
	}

	/**
	 * This method is invoked by the scheduler service to fetch updated news
	 * articles in a particular interval period.
	 * 
	 * @since 2020-04-30
	 * @author Sujan Kumar Mitra
	 * @see SchedulerService
	 */
	public void getUpdatedArticles() {
		logger.info("Updating news articles");
		categories.forEach((category) -> {
			threadPoolService.getThread(() -> {
				try {
					newsMap.replace(category, getArticlesFromCategory(category));
				} catch (NewsNotFoundException e) {
					logger.warning("Could not get articles of " + category);
					logger.warning(e.getLocalizedMessage());
				}
			}, category.getCategory()).start();
		});
		new Thread(() -> notifyWaitingThreads(), "Notifier").start();
	}

	/**
	 * This method is invoked by the Spring Container after the instance of this
	 * class is created. This method calls the API provider to fetch news articles
	 * of different categories and store it in the news map.
	 * 
	 * @since 2020-04-30
	 * @author Sujan Kumar Mitra
	 * @see NewsService#getArticlesFromCategory(Category)
	 */
	@PostConstruct
	private void init() {
		logger.info("Fetching news");
		categories.forEach((category) -> {
			threadPoolService.getThread(() -> {
				try {
					newsMap.put(category, getArticlesFromCategory(category));
				} catch (NewsNotFoundException e) {
					logger.warning("Could not get articles of " + category);
				}
			}, category.getCategory()).start();
		});
		new Thread(() -> notifyWaitingThreads(), "Notifier").start();
	}

	/**
	 * This method notifies the waiting threads that the news fetch is complete and
	 * threads can now access the {@link NewsService#newsMap}
	 * 
	 * @since 2020-05-23
	 * @author Sujan Kumar Mitra
	 */
	private synchronized void notifyWaitingThreads() {
		while (!threadPoolService.areAllThreadsDead()) {
		}
		notifyAll();
		logger.info("News fetching complete");
		threadPoolService.removeAllThreads();

	}

}
