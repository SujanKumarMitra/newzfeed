package com.herokuapp.newsfeedapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.herokuapp.newsfeedapp.entity.UserDetailsImpl;
import com.herokuapp.newsfeedapp.repository.UserDetailsImplRepository;

/**
 * This class is an implementation of {@link UserDetailsService}. <br>
 * Contains a method {@link UserDetailsServiceImpl#loadUserByUsername(String)
 * loadUserByUsername} for loading user information based on given username.
 * 
 * @since 2020-05-15
 * @author Sujan Kumar Mitra
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	/**
	 * Repository interface for performing query.
	 * 
	 * @since 2020-05-15
	 * @author Sujan Kumar Mitra
	 * @see UserDetailsImplRepository
	 */
	@Autowired
	private UserDetailsImplRepository userDetailsRepository;

	/**
	 * This method returns the {@link UserDetailsImpl} object based on username
	 * provided
	 * 
	 * @since 2020-05-15
	 * @author Sujan Kumar Mitra
	 * @see UserDetailsService#loadUserByUsername(String)
	 * 
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserDetailsImpl> userDetails = userDetailsRepository.findByUsername(username);		
		return userDetails.orElseThrow(()->new UsernameNotFoundException("No user found with username := "+username));
	}

}
