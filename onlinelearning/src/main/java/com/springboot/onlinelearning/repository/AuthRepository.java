package com.springboot.onlinelearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.springboot.onlinelearning.model.User;

public interface AuthRepository extends JpaRepository<User, Integer>{

	User findByUsername(String username);

}
