package com.springboot.onlinelearning.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.onlinelearning.model.Student;
import com.springboot.onlinelearning.model.User;
import com.springboot.onlinelearning.repository.AuthRepository;
import com.springboot.onlinelearning.service.AuthService;
import com.springboot.onlinelearning.service.StudentService;

@RestController
@RequestMapping("api/student")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private AuthRepository authRepository;
	
	@GetMapping("/all")
	public List<Student> getAll() {
		return studentService.getAll();
	}
	@GetMapping("/findbyname/{name}")
	public List<Student> getByName(@PathVariable String name){
		return studentService.getByName(name);
	}
	
	
	
	@PostMapping("/add/{userid}")
	public Student addStudent(@PathVariable int userid,@RequestBody Student student) {
		User user = authService.getUserId(userid);
		authRepository.save(user);
		student.setUser(user);
		
		return studentService.addStudent(student);
		
	}
	
	@PutMapping("/update/{studid}")
	public Student updateStudent(@PathVariable int studid,@RequestBody Student student) {
		Student studentDB = studentService.getStudentid(studid);
		studentDB.setName(student.getName());
		studentDB.setEmail(student.getEmail());
		studentDB.setContact(student.getContact());
		studentDB.setDate_joined(student.getDate_joined());
		
		return studentService.updateStudent(studentDB);
	}
	
	
	
	
	
	

}
