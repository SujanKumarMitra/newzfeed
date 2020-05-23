package com.herokuapp.newsfeedapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is the starting class of the application.
 * 
 * @since 2020-04-30
 * @author Sujan Kumar Mitra
 */
@SpringBootApplication
public class NewsfeedAppApplication {

	/**
	 * This method starts the application and performs the following operations.
	 * <ul>
	 * <li>Bootstraps of the application</li>
	 * <li>Starting embedded servlet container</li>
	 * <li>Creating JDBC Connection pool</li>
	 * <li>Mapping URL patterns</li>
	 * </ul>
	 * 
	 * @param args command line arguments
	 * @since 2020-04-30
	 * @author Sujan Kumar Mitra
	 * @see SpringApplication
	 */
	public static void main(String[] args) {
		SpringApplication.run(NewsfeedAppApplication.class, args);
	}

}
