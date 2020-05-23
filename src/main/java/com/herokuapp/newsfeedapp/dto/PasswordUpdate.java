package com.herokuapp.newsfeedapp.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.herokuapp.newsfeedapp.util.PasswordMatches;
import com.herokuapp.newsfeedapp.util.PasswordValidation;
import com.herokuapp.newsfeedapp.util.PasswordVerify;

/**
 * This class is used to hold data for updating user password.
 * 
 * @since 2020-05-22
 * @author Sujan Kumar Mitra
 *
 */
@PasswordMatches(fieldName = "confirmPassword", message = "Passwords don't match")
public class PasswordUpdate implements PasswordValidation{
	
	@NotEmpty(message = "Can't be empty")
	@NotBlank(message = "Can't be blank")
	@PasswordVerify(message = "Wrong password")
	private String oldPassword;
	
	@NotEmpty(message = "Can't be empty")
	@Size(min = 8, max = 32, message = "Minimum 8 characters, Maximum 32 characters")
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,32}", message = "Must contain at least one lowercase character, one uppercase character, one digit, one special character")
	private String newPassword;
	
	@NotEmpty(message = "Can't be empty")
	@NotBlank(message = "Can't be blank")
	private String confirmPassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public String getPassword() {
		return getNewPassword();
	}

	@Override
	public String getMatchingPassword() {
		return getConfirmPassword();
	}

	@Override
	public String toString() {
		return "PasswordUpdate [oldPassword=" + oldPassword + ", newPassword=" + newPassword + ", confirmPassword="
				+ confirmPassword + "]";
	}
	
	

}
