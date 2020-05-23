package com.herokuapp.newsfeedapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.herokuapp.newsfeedapp.controller.SignupController;
import com.herokuapp.newsfeedapp.dto.SignupUser;
import com.herokuapp.newsfeedapp.entity.UserDetailsImpl;
import com.herokuapp.newsfeedapp.exception.UserAlreadyExistsException;
import com.herokuapp.newsfeedapp.repository.UserDetailsImplRepository;

/**
 * This class is used to perform signup activities.
 * 
 * @since 2020-05-15
 * @author Sujan Kumar Mitra
 * @see SignupController
 */
@Service
public class SignupService {

	/**
	 * This member is used to perform hashing on the password
	 * 
	 * @see BCryptPasswordEncoder
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * For database interactions.
	 */
	@Autowired
	private UserDetailsImplRepository userDetailsRepository;

	/**
	 * This method performs the signup operation.
	 * 
	 * @param signupUser the user to signup
	 * @return the instance of {@link UserDetailsImpl} signed up.
	 * @throws UserAlreadyExistsException will throw if user with username already
	 *                                    exists in the database
	 */
	public UserDetailsImpl signup(SignupUser signupUser) throws UserAlreadyExistsException {
		boolean existsByUsername = userDetailsRepository.existsByUsername(signupUser.getEmail());
		if (existsByUsername) {
			throw new UserAlreadyExistsException("User with email := " + signupUser.getEmail() + " already exists");
		}
		signupUser.setPassword(passwordEncoder.encode(signupUser.getPassword()));
		UserDetailsImpl userDetailsImpl = new UserDetailsImpl(signupUser);
		return userDetailsRepository.save(userDetailsImpl);
	}

}
