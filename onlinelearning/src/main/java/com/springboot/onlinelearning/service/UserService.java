package com.springboot.onlinelearning.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.springboot.onlinelearning.model.Student;
import com.springboot.onlinelearning.model.User;
import com.springboot.onlinelearning.repository.AuthRepository;
@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	private AuthRepository authRepository;

	public UserDetails loadUserByUsername(String username) {
		// TODO Auto-generated method stub
		return (UserDetails) authRepository.findByUsername(username);
	}


	 

	

}
