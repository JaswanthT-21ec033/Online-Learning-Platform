package com.springboot.onlinelearning.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.onlinelearning.config.JwtUtil;
import com.springboot.onlinelearning.dto.TokenDto;
import com.springboot.onlinelearning.exception.InvalidUsernameException;
import com.springboot.onlinelearning.model.Student;
import com.springboot.onlinelearning.model.User;
import com.springboot.onlinelearning.service.AuthService;
import com.springboot.onlinelearning.service.UserService;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private AuthService authService;

	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/signup")
	public User signUp(@RequestBody User user) throws InvalidUsernameException {
		return authService.signUp(user);
	}
	@PostMapping("/login")
	public UserDetails login(Principal principal) {
		String username = principal.getName();
		return userService.loadUserByUsername(username);
	}

	@PostMapping("/token/generate")
	public TokenDto generateToken(@RequestBody User user,TokenDto dto) {
		Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
		authenticationManager.authenticate(auth);
		String token =  jwtUtil.generateToken(user.getUsername()); 
		dto.setToken(token);
		dto.setUsername(user.getUsername());
		dto.setExpiry(jwtUtil.extractExpiration(token).toString());
		return dto; 
	}
	
	@GetMapping("/user/details")
	public UserDetails getUserDetails(Principal principal) {
		String username = principal.getName();
		return userService.loadUserByUsername(username);
	}
	
}