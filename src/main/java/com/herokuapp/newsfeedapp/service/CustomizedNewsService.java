package com.herokuapp.newsfeedapp.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.herokuapp.newsfeedapp.entity.UserDetailsImpl;
import com.herokuapp.newsfeedapp.model.Article;
import com.herokuapp.newsfeedapp.model.Category;

/**
 * This class is used to fetch articles of categories the user is subscribed to.<br>
 * Since the news map is huge, and it requires a lot of computation power to
 * filter articles of categories the user is subscribed and put it in a list on every request, <br>
 * the scope of the class is set to session, meaning this bean will be created
 * for every new session. <br> When bean is created then it will store the articles of user's subscription list in
 * {@link CustomizedNewsService#articles} list.<br> Then it will serve the articles from the list.<br>
 * This {@link CustomizedNewsService#articles} will be refreshed
 * <ul>
 * <li>on every scheduler trigger</li>
 * <li>when user updates it's category subscription list.</li>
 * </ul>
 * 
 * @since  2020-05-19
 * @author Sujan Kumar Mitra
 *
 */
@Service
@Scope("session")
public class CustomizedNewsService {

	/**
	 * to fetch news map
	 */
	@Autowired
	private NewsService newsService;

	/**
	 * to store its reference when instance is created
	 * @see SessionBeanService
	 */
	@Autowired
	private SessionBeanService sessionBeanHolder;
	
	/**
	 * holds logged in user information
	 * @see UserDetailsImpl
	 */
	private UserDetailsImpl userDetails;

	/**
	 * no of articles to return
	 */
	@Value("${articles.length}")
	private int pageSize;

	/**
	 * to store the articles of user's subscription
	 */
	private List<Article> articles;

	/**
	 * this method is invoked when Spring container is created the instance
	 */
	@PostConstruct
	private void init() {
		sessionBeanHolder.addSessionBean(this);
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.setUserDetails(userDetails);
		this.setArticles(this.getArticlesOfUserCategories());
	}

	/**
	 * this method is invoked when spring container will destroy the bean
	 */
	@PreDestroy
	private void destroy() {
		sessionBeanHolder.removeSessionBean(this);
	}

	/**
	 * This method returns the list of articles of categories the user is currently subscribed to.
	 * @return list of articles
	 */
	private List<Article> getArticlesOfUserCategories() {
		List<Category> categories = userDetails.getUser().getCategories();
		if (categories == null || categories.isEmpty()) {
			return null;
		}
		/*
		 * Filtering the articles of categories of user
		 */
		List<Article> articles = newsService.getNewsMap().entrySet().parallelStream()
				.filter(entry -> categories.contains(entry.getKey()))
				.flatMap(entry -> entry.getValue().stream())
				.collect(Collectors.toList());

		Collections.shuffle(articles);
		return articles;
	}

	/**
	 * This method return the articles according to page size
	 * @param page the requested page size
	 * @return list of articles to controller
	 */
	public List<Article> getArticles(int page) {
		if (articles.size() < page * pageSize) {
			return null;
		} else if (articles.size() <= page * pageSize + pageSize) {
			return articles.subList(page * pageSize, articles.size());
		} else {
			return articles.subList(page * pageSize, page * pageSize + pageSize);
		}
	}

	/**
	 * This method checks if the user has subscribed to any categories or not
	 * @return if subscribed to one or more than one then <strong>false</strong>, otherwise <strong>true</strong>
	 */
	public boolean isUserNotSubscribedToAnyCategory() {
		return userDetails.getUser().getCategories().isEmpty();
	}

	/**
	 * invoked by
	 * <ul>
	 * <li>the scheduler service to refresh the articles</li>
	 * <li>if user updates the category list</li>
	 * </ul>
	 */
	public void refreshList() {
		new Thread(()->setArticles(getArticlesOfUserCategories())).start();
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
	public UserDetailsImpl getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetailsImpl userDetails) {
		this.userDetails = userDetails;
	}

}
