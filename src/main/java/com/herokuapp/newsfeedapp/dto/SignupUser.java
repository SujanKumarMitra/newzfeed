package com.herokuapp.newsfeedapp.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.herokuapp.newsfeedapp.util.PasswordMatches;
import com.herokuapp.newsfeedapp.util.PasswordValidation;

/**
 * This class acts as a model class for signup action
 * 
 * @author Sujan Kumar Mitra
 * @since 2020-05-15
 */
@PasswordMatches(fieldName = "confirmPassword", message = "Passwords don't match")
public class SignupUser implements PasswordValidation{

	/**
	 * first name of the user, must contain at-least one character
	 */
	@NotEmpty(message = "Can't be empty")
	@NotBlank(message = "Can't be blank")
	private String firstName;

	/**
	 * last name of the user, must contain at-least one character
	 */
	@NotEmpty(message = "Can't be empty")
	@NotBlank(message = "Can't be blank")
	private String lastName;

	/**
	 * email address of the user. the email pattern complies with RFC 5322
	 */
	@NotEmpty(message = "Can't be empty")
	@NotBlank(message = "Can't be blank")
	@Pattern(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Not a valid email address")
	private String email;

	/**
	 * the password of the user.<br>
	 * Must contain
	 * <ul>
	 * <li>at least one lowercase character</li>
	 * <li>at least one uppercase character</li>
	 * <li>at least one digit</li>
	 * <li>at least one special character</li>
	 * <li>length between 8 to 32</li>
	 * </ul>
	 * 
	 */
	@NotEmpty(message = "Can't be empty")
	@Size(min = 8,max = 32, message = "Minimum 8 characters, maximum 32 characters")
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,32}", message = "Must contain at least one lowercase character, one uppercase character, one digit, one special character")
	private String password;

	/**
	 * used for {@link PasswordMatches} validation
	 */
	@NotEmpty(message = "Can't be empty")
	@NotBlank(message = "Can't be blank")
	private String confirmPassword;

	public SignupUser() {
		super();
	}

	public SignupUser(String firstName, String lastName, String email, String password, String confirmPassword) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public String toString() {
		return "SignupUser [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", password="
				+ password + ", confirmPassword=" + confirmPassword + "]";
	}

	@Override
	public String getMatchingPassword() {
		return getConfirmPassword();
	}

}
