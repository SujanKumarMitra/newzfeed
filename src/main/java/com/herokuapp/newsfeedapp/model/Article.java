package com.herokuapp.newsfeedapp.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

/**
 * This class holds the data of article
 * 
 * @since 2020-04-30
 * @author Sujan Kumar Mitra
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Article {

	@JsonProperty("source")
	private Source source;

	@JsonProperty("author")
	private String author;

	@JsonProperty("title")
	private String title;

	@JsonProperty("description")
	private String description;

	@JsonProperty("url")
	private String url;

	@JsonProperty("urlToImage")
	private String urlToImage;

	@JsonProperty("publishedAt")
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime publishedAt;

	@JsonProperty("content")
	private String content;

	@JsonProperty("source")
	public Source getSource() {
		return source;
	}

	@JsonProperty("source")
	public void setSource(Source source) {
		this.source = source;
	}

	@JsonProperty("author")
	public String getAuthor() {
		return author;
	}

	@JsonProperty("author")
	public void setAuthor(String author) {
		if (author == null) {
			this.author = "Author not found";
		} else {
			this.author = author;
		}
	}

	@JsonProperty("title")
	public String getTitle() {
		return title;
	}

	@JsonProperty("title")
	public void setTitle(String title) {
		/*
		 * Adjusting length of string for view
		 */
		if (title == null) {
			this.title = "Title not found";
		} else {
			this.title = title.length() >= 40 ? title.substring(0, 40).concat("...") : title;
		}
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		if (description == null) {
			this.description = " Description not found";
		} else {
			this.description = description;
		}
	}

	@JsonProperty("url")
	public String getUrl() {
		return url;
	}

	@JsonProperty("url")
	public void setUrl(String url) {
		if (url == null) {
			this.url = "#";
			return;
		} else {
			this.url = url;
		}
	}

	@JsonProperty("urlToImage")
	public String getUrlToImage() {
		return urlToImage;
	}

	@JsonProperty("urlToImage")
	public void setUrlToImage(String urlToImage) {
		if (urlToImage == null) {
			this.urlToImage = "#";
		} else {
			this.urlToImage = urlToImage;
		}
	}

	@JsonProperty("publishedAt")
	public LocalDateTime getPublishedAt() {
		return publishedAt;
	}

	@JsonProperty("publishedAt")
	public void setPublishedAt(LocalDateTime publishedAt) {
		this.publishedAt = publishedAt;
	}

	@JsonProperty("content")
	public String getContent() {
		return content;
	}

	@JsonProperty("content")
	public void setContent(String content) {
		if (content == null) {
			this.content = " Content not found";
		} else {
			this.content = content;
		}
	}

	/**
	 * This method is invoked by the view technology to get formatted date
	 * 
	 * @return formatted String 
	 * @since 2020-05-09
	 * @author Sujan Kumar Mitra
	 */
	public String getPublishedDate() {
		return publishedAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm a"));
	}

	@Override
	public String toString() {
		return "Article [source=" + source + ", author=" + author + ", title=" + title + ", description=" + description
				+ ", url=" + url + ", urlToImage=" + urlToImage + ", publishedAt=" + publishedAt + ", content="
				+ content + "]";
	}

}