package com.springboot.onlinelearning.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.springboot.onlinelearning.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
 

@Component
public class JwtFilter extends OncePerRequestFilter{
 
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserService UserService ;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		 try {
	
		final String authorizationHeader = request.getHeader("Authorization");
		
		// final String authorizationHeader = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2ZW5kb3IiLCJleHAiOjE3NDU2NTM2OTksImlhdCI6MTc0NTU2NzI5OX0.gy3L32L14Jpupp8-WamPhBXx_TN9cekl0TFWJL6nlOY";
		 System.out.println("Auth Header " + authorizationHeader);
		
		 String username = null;
	     String jwt = null;
 
	        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	            jwt = authorizationHeader.substring(7);
	            username = jwtUtil.extractUsername(jwt);
	            System.out.println(username);
	        }
	        
	        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
 
	            UserDetails userDetails = this.UserService.loadUserByUsername(username);
 
	            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
 
	                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
	                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	                usernamePasswordAuthenticationToken
	                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	            }
	            
	        }
	        filterChain.doFilter(request, response);
		 }
		 catch(Exception e) {
			 System.out.println(e.getMessage());
			
		 }
	}
 
}