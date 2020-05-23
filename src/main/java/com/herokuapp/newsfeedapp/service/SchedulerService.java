package com.herokuapp.newsfeedapp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.herokuapp.newsfeedapp.exception.NewsNotFoundException;
import com.herokuapp.newsfeedapp.model.Article;

/**
 * This class performs scheduled operations.
 * 
 * @since 2020-05-12
 * @author Sujan Kumar Mitra
 *
 */
@Service
public class SchedulerService {

	/**
	 * The newsService class
	 */
	@Autowired
	private NewsService newsService;

	/**
	 * The headline service
	 */
	@Autowired
	private HeadlineService headlineService;
	
	/**
	 * session bean holder service
	 */
	@Autowired
	private SessionBeanService sessionBeanService;

	/**
	 * For logging purposes
	 */
	private Logger logger = Logger.getLogger(getClass().getName());

	/**
	 * This method is invoked by the Spring container in a interval of 1 hour.
	 * 
	 * @since 2020-05-12
	 * @author Sujan Kumar Mitra
	 * @throws NewsNotFoundException might throw if news is not found.
	 */
	@Scheduled(cron = "0 0 * ? * *")
	private void refreshArticles() throws NewsNotFoundException {
		new Thread(this::execute,"Scheduler").start();
	}

	/**
	 * Performs the following operations.
	 * <ul>
	 * <li>Fetches updated news articles from the api and puts it in news map</li>
	 * <li>Refreshes headlines list.</li>
	 * <li>Refreshes articles of logged in user's custom list</li>
	 * <li>Invokes the garbage collector to perform clean up</li>
	 * </ul>
	 * 
	 * @since 2020-05-12
	 * @author Sujan Kumar Mitra
	 * @see NewsService#getUpdatedArticles()
	 * @see SchedulerService#refreshHeadlines()
	 */
	private void execute() {
		logger.info("Starting Updation process");
		logger.info("Getting updated articles");
		newsService.getUpdatedArticles();
		logger.info("Refreshing Headlines");
		this.refreshHeadlines();
		logger.info("Refreshing User's custom article source");
		sessionBeanService.getSessionBeans().parallelStream().forEach(bean -> bean.refreshList());
		logger.info("Running garbage collector");
		System.gc();
		logger.info("Finished Updation process");

	}

	/**
	 * This method refreshes the headlines list.
	 * 
	 * @since 2020-05-12
	 * @author Sujan Kumar Mitra
	 * @see HeadlineService
	 */
	private void refreshHeadlines() {
		Collection<List<Article>> values = newsService.getNewsMap().values();
		List<Article> articles = new ArrayList<>();
		values.parallelStream().forEach(articles::addAll);
		Collections.shuffle(articles);
		headlineService.setArticles(articles);
	}

}
