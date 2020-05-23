package com.herokuapp.newsfeedapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.herokuapp.newsfeedapp.entity.UserDetailsImpl;

/**
 * This interface provides methods for quering the database table user_details
 * and user_roles.<br>
 * Implementations are provided by Spring Data JPA in runtime.
 * 
 * @author Sujan Kumar Mitra
 * @since 2020-05-15
 *
 */
@Repository
public interface UserDetailsImplRepository extends JpaRepository<UserDetailsImpl, Long> {

	/**
	 * This method returns {@link UserDetailsImpl} if exists by the username
	 * provided.
	 * 
	 * @param username username of the user
	 * @return instance of {@link Optional} that might contain {@link UserDetailsImpl} depending on the parameter.
	 */
	Optional<UserDetailsImpl> findByUsername(String username);

	/**
	 * This method is used to chech whether a user exists by the username or not
	 * 
	 * @param username username of the user
	 * @return if exists then <strong>true</strong> else <strong>false</strong>
	 */
	boolean existsByUsername(String username);

}
