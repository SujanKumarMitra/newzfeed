package com.herokuapp.newsfeedapp.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.herokuapp.newsfeedapp.entity.User;
import com.herokuapp.newsfeedapp.model.Category;

/**
 * This class is used for updating user profile information.
 * 
 * @since 2020-05-17
 * @author Sujan Kumar Mitra
 *
 */
public class UserProfile {

	private long id;

	@NotEmpty(message = "Can't be empty")
	@NotBlank(message = "Can't be blank")
	private String firstName;

	@NotEmpty(message = "Can't be empty")
	@NotBlank(message = "Can't be blank")
	private String lastName;

	private List<Category> categories;

	public UserProfile() {
		super();
	}

	public UserProfile(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public UserProfile(long id, String firstName, String lastName) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public UserProfile(User user) {
		this.id = user.getId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.categories = user.getCategories();

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	@Override
	public String toString() {
		return "UserProfile [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", categories="
				+ categories + "]";
	}

}
