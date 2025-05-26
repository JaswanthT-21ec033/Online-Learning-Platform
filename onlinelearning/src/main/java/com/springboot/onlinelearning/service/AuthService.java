package com.springboot.onlinelearning.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.onlinelearning.exception.InvalidUsernameException;
import com.springboot.onlinelearning.model.User;
import com.springboot.onlinelearning.repository.AuthRepository;

@Service
public class AuthService {
	@Autowired
	private AuthRepository authRepository;

	@Autowired
	private BCryptPasswordEncoder bcrypt;

	public User signUp(User user) throws InvalidUsernameException { 
 		User user1 =  authRepository.findByUsername(user.getUsername());
 		if(user1 != null) { 
 			throw new InvalidUsernameException("Username already exists");
 		}
		if (user.getRole() == null)
			user.setRole(user.getRole());
		String encodedPass = bcrypt.encode(user.getPassword());
		user.setPassword(encodedPass);
		return authRepository.save(user);
	}

	public User getUserId(int userid) {
		Optional<User> optional = authRepository.findById(userid);
		if(optional.isEmpty())
			throw new RuntimeException("Invalid User ID: "+userid);
		
		return optional.get();
	}

	

}
