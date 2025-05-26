package com.springboot.onlinelearning.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.springboot.onlinelearning.model.Instructor;
import com.springboot.onlinelearning.model.Student;
import com.springboot.onlinelearning.model.User;
import com.springboot.onlinelearning.repository.AuthRepository;
import com.springboot.onlinelearning.service.AuthService;
import com.springboot.onlinelearning.service.InstructorService;
import com.springboot.onlinelearning.service.StudentService;
import com.springboot.onlinelearning.service.UserService;

@RestController
@RequestMapping("api/instructor")
public class InstructorController {
	
	@Autowired
	private InstructorService instructorService;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthRepository authRepository ;
	
	@PostMapping("/add/{userid}")
	public Instructor addInstructor (@PathVariable int userid,@RequestBody Instructor instructor) {
		User user = authService.getUserId(userid);
		authRepository.save(user);
		instructor.setUser(user);
		return instructorService.addInstructor(instructor);
	}
	
	@GetMapping("/getbyid/{instructid}")
	public Instructor getById(@PathVariable int instructid){
		return instructorService.getById(instructid);
	}
	
	@GetMapping("/all")
	public List<Instructor> getAll() {
		return instructorService.getAll();
	}
	
	@GetMapping("/getbyname/{name}")
	public List<Instructor> getByName(@PathVariable String name) {
		return instructorService.getByName(name);
	}
	
	@PutMapping("update/{instructid}")
	public Instructor updateInstructor(@PathVariable int instructid,@RequestBody Instructor instructor) {
		Instructor instructorDB = instructorService.getInstructorId(instructid);
		instructorDB.setName(instructor.getName());
		instructorDB.setEmail(instructor.getEmail());
		instructorDB.setContact(instructor.getContact());
		instructorDB.setJoined_At(instructor.getJoined_At());
		instructorDB.setBio(instructor.getBio());
		return instructorService.updateInstructor(instructorDB);
	}
	
	@GetMapping("/profile")
	public  Instructor getInstructorProfile(Principal principal) {
		String username = principal.getName();
		 return instructorService.getByUsername(username);
	}


	
}
