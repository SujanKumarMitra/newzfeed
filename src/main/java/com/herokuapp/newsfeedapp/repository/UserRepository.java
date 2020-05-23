package com.herokuapp.newsfeedapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.herokuapp.newsfeedapp.entity.User;

/**
 * This interface provides method for quering the database table users. <br>
 * Implementations are provided by Spring Data during runtime.
 * 
 * @author Sujan Kumar Mitra
 * @since 2020-05-15
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
