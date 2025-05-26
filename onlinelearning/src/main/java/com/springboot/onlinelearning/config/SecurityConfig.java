package com.springboot.onlinelearning.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.springboot.onlinelearning.service.UserService;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtFilter jwtFilter;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.cors(withDefaults())
		.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/api/auth/token/generate").permitAll()
						
						.requestMatchers("/api/instructor/add").hasAuthority("INSTRUCTOR")
						.requestMatchers("/api/instructor/getbyid/{instructid}").hasAuthority("INSTRUCTOR")
						.requestMatchers("/api/instructor/all").hasAuthority("INSTRUCTOR")
						.requestMatchers("api/instructor/getbyname/{name}").hasAuthority("INSTRUCTOR")
						.requestMatchers("api/instructor/update/{instructid}").hasAuthority("INSTRUCTOR")
						
						.requestMatchers("api/course/all").permitAll()
						.requestMatchers("api/course/getbyid/{courseid}").permitAll()
						.requestMatchers("api/course/getbycoursename/{coursename}").permitAll()
						.requestMatchers("api/course/add").permitAll()
						.requestMatchers("api/course/update/{courseid}").permitAll()
						.requestMatchers("api/course/assign/{instructorid}").hasAnyAuthority("INSTRUCTOR","ADMIN")
						
						.requestMatchers("api/enrollment/all").permitAll()
						.requestMatchers("api/enrollment/getbyid/{enrollid}").hasAnyAuthority("STUDENT","ADMIN")
						.requestMatchers("api/enrollment/add").hasAnyAuthority("STUDENT","ADMIN")
						.requestMatchers("api/enrollment/update/{enrollid}").hasAnyAuthority("STUDENT","ADMIN")
						.requestMatchers("api/enrollment/delete/{enroll_id}").hasAnyAuthority("STUDENT","ADMIN")
						.requestMatchers("api/enrollment/assign/{courseid}/{studentid}").hasAnyAuthority("STUDENT","ADMIN")
						
						
						.requestMatchers("/api/student/all").permitAll()
						.requestMatchers("/api/student/findbyname/{name}").hasAnyAuthority("STUDENT","ADMIN")
						.requestMatchers("/api/student/add/{userid}").hasAnyAuthority("STUDENT","ADMIN")
						.requestMatchers("/api/student/update/{studid}").hasAnyAuthority("STUDENT","ADMIN")
						
						.requestMatchers("api/assignment/all").permitAll()
						.requestMatchers("api/assignment/all/assign/{courseid}/{studid}").hasAuthority("INSTRUCTOR")
						.requestMatchers("api/assignment/add").hasAuthority("INSTRUCTOR")
						.requestMatchers("api/assignment/deletebyassignid/{assignId}").hasAuthority("INSTRUCTOR")
						.requestMatchers("api/assignment/getbyassignmenttitle/{title}").hasAuthority("INSTRUCTOR")
						.requestMatchers("api/assignment/grade/{grade}").hasAuthority("INSTRUCTOR")
						.requestMatchers("api/assignment/update/{assignid}").hasAuthority("INSTRUCTOR")
						.requestMatchers("api/assignment/assign/{courseid}/{instructorid}").hasAuthority("INSTRUCTOR")
						
						.requestMatchers("api/auth/signup").permitAll()
						.requestMatchers("api/auth/token/generate").permitAll()
						.requestMatchers("api/auth/profile").hasAnyAuthority("INSTRUCTOR","STUDENT")
						.anyRequest().permitAll())
				
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
	
	
	@Bean
	UrlBasedCorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
	    configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
	    configuration.setAllowedHeaders(Arrays.asList("*")); // Using Authorization Headers in the Front UI
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	}

	@Bean
	AuthenticationProvider getAuth() {
		DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
		dao.setPasswordEncoder(passEncoder());
		dao.setUserDetailsService(userService);
		return dao;
	}

	@Bean
	BCryptPasswordEncoder passEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager getAuthManager(AuthenticationConfiguration auth) throws Exception {
		return auth.getAuthenticationManager();
	}

}
