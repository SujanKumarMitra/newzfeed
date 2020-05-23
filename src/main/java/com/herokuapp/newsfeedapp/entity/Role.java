package com.herokuapp.newsfeedapp.entity;

/**
 * This enum contains roles list provided to users. Roles are managed by Spring
 * Security.
 * 
 * @author Sujan Kumar Mitra
 * @since 2020-05-15
 */
public enum Role {
	/**
	 * users who signup in the application.
	 */
	USER("ROLE_USER"),
	/**
	 * contains admin privileges like access to actuator endpoints.
	 */
	ADMIN("ROLE_ADMIN");

	private String role;

	Role(String role) {
		this.role = role;
	}

	/**
	 * This method is getter method, return string value of enum.
	 * 
	 * @return string value of enum.
	 * @since 2020-05-15
	 * @author Sujan Kumar Mitra
	 */
	public String getRole() {
		return role;
	}

}
