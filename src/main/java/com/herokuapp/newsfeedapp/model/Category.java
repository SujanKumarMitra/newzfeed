package com.herokuapp.newsfeedapp.model;

import com.herokuapp.newsfeedapp.service.CategoryService;

/**
 * This enum contain supported categories.
 * 
 * @since 2020-05-11
 * @author Sujan Kumar Mitra
 * @see CategoryService
 */
public enum Category {
	BUSINESS("business"), ENTERTAINMENT("entertainment"), GENERAL("general"), HEALTH("health"), SCIENCE("science"),
	SPORTS("sports"), TECHNOLOGY("technology"), COVID("covid");

	private String category;

	Category(String category) {
		this.category = category;
	}

	/**
	 * @return {@link String } value of enum
	 * @since 2020-05-11
	 * @author Sujan Kumar Mitra
	 */
	public String getCategory() {
		return category.toString();
	}
}
