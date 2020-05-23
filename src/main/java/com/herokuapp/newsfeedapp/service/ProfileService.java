package com.herokuapp.newsfeedapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.herokuapp.newsfeedapp.dto.PasswordUpdate;
import com.herokuapp.newsfeedapp.entity.User;
import com.herokuapp.newsfeedapp.entity.UserDetailsImpl;
import com.herokuapp.newsfeedapp.repository.UserDetailsImplRepository;

/**
 * This class is used to perform profile related activities.
 * 
 * @since 2020-05-17
 * @author Sujan Kumar Mitra
 *
 */
@Service
public class ProfileService {

	/**
	 * To interact with the {@link UserDetailsImpl} table.
	 */
	@Autowired
	private UserDetailsImplRepository userDetailsRepository;
	
	/**
	 * to get beans from Spring Bean Factory
	 */
	@Autowired
	private ApplicationContext applicationContext;
	
	/**
	 * for password hashing
	 */
	@Autowired
	private PasswordEncoder encoder;

	/**
	 * This method performs the updation of user profile.
	 * 
	 * @param user the user object to update
	 * @since 2020-05-17
	 * @author Sujan Kumar Mitra
	 */
	public void updateProfile(User user) {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		userDetails.setUser(user);
		userDetailsRepository.save(userDetails);
		/*
		 * Refresh customized news list, due to changes the user might have made in categories
		 */
		CustomizedNewsService newsService = applicationContext.getBean(CustomizedNewsService.class);
		newsService.refreshList();
	}
	
	public void updatePassword(PasswordUpdate passwordUpdate) {
		SecurityContext context = SecurityContextHolder.getContext();
		UserDetailsImpl userDetails = (UserDetailsImpl) context.getAuthentication()
				.getPrincipal();
		String password = encoder.encode(passwordUpdate.getPassword());
		userDetails.setPassword(password);
		userDetailsRepository.save(userDetails);
		context.setAuthentication(null);
		SecurityContextHolder.clearContext();
	}

}
