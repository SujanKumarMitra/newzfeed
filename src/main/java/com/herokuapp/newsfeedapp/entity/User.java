package com.herokuapp.newsfeedapp.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.herokuapp.newsfeedapp.dto.SignupUser;
import com.herokuapp.newsfeedapp.dto.UserProfile;
import com.herokuapp.newsfeedapp.model.Category;

/**
 * This class contains additional data related of users.
 * 
 * @author Sujan Kumar Mitra
 * @since 202-05-15
 */
@Entity
@Table(name = "users")
public class User implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	/**
	 * id of the user
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	/**
	 * first name of the user
	 */
	@Column(name = "first_name")
	private String firstName;

	/**
	 * last name of the user
	 */
	@Column(name = "last_name")
	private String lastName;

	/**
	 * security details of the user. has injective mapping to userDetails
	 * 
	 * @see UserDetailsImpl
	 */
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_details_id")
	private UserDetailsImpl userDetails;

	/**
	 * list of categories the user is subscribed to.
	 * 
	 * @see Category
	 */
	@ElementCollection(targetClass = Category.class, fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	@CollectionTable(name = "user_categories", joinColumns = @JoinColumn(name = "user_id"))
	@Fetch(FetchMode.SELECT)
	private List<Category> categories;

	public User() {
		super();
	}

	public User(SignupUser signupUser) {
		this.id = 0;
		this.firstName = signupUser.getFirstName();
		this.lastName = signupUser.getLastName();
	}

	public User(UserProfile updateUser) {
		this.id = updateUser.getId();
		this.firstName = updateUser.getFirstName();
		this.lastName = updateUser.getLastName();
		this.categories = updateUser.getCategories();
	}

	public User(OAuth2User oAuth2User) {
		String name = (String) oAuth2User.getAttribute("name");
		String[] split = name.split(" ");
		String firstName = "";
		for (int i = 0; i < split.length - 1; i++) {
			firstName = firstName.concat(split[i]).concat(" ");
		}
		String lastName = split[split.length - 1];
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = 0;
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

	public UserDetailsImpl getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetailsImpl userDetails) {
		this.userDetails = userDetails;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", categories=" + categories
				+ "]";
	}

}
