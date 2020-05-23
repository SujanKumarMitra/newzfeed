package com.herokuapp.newsfeedapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.herokuapp.newsfeedapp.exception.NewsNotFoundException;
import com.herokuapp.newsfeedapp.model.Article;
import com.herokuapp.newsfeedapp.model.News;

/**
 * This class is used to form search operations.
 * 
 * @since 2020-05-05
 * @author Sujan Kumar Mitra
 *
 */
@Service
public class SearchService {

	/**
	 * Base url of API provider
	 */
	@Value("${api.url.everything}")
	private String baseUrl;

	/**
	 * Secret token to access API provider
	 */
	@Value("${api.key}")
	private String apiKey;

	/**
	 * This member holds the number of articles to return per page
	 */
	@Value("${articles.length}")
	private int pageSize;

	/**
	 * For de-serializing the response provided by the API.
	 */
	@Autowired
	private ObjectMapper mapper;

	/**
	 * For RESTful communications.
	 */
	@Autowired
	private RestTemplate template;

	/**
	 * This method returns the articles based on the query string passed
	 * 
	 * @param query the query string
	 * @param page  requested page
	 * @return list of articles
	 * @throws NewsNotFoundException might throw this if news articles not found
	 * @since 2020-05-05
	 * @author Sujan Kumar Mitra
	 */
	public List<Article> getArticles(String query, int page) throws NewsNotFoundException {
		String url = baseUrl + "&q=" + query + "&page=" + page + "&pageSize=" + pageSize;
		ResponseEntity<String> responseEntity = template.exchange(url, HttpMethod.GET, null, String.class);
		if (responseEntity.getStatusCode().value() == 200) {
			try {
				News news = mapper.readValue(responseEntity.getBody(), News.class);
				return news.getArticles();
			} catch (JsonProcessingException e) {
				return null;
			}
		} else {
			throw new NewsNotFoundException();
		}

	}

}
