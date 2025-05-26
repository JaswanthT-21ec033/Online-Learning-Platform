package com.springboot.onlinelearning.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.onlinelearning.model.Course;
import com.springboot.onlinelearning.model.Enrollment;
import com.springboot.onlinelearning.model.Student;
import com.springboot.onlinelearning.service.CourseService;
import com.springboot.onlinelearning.service.EnrollmentService;
import com.springboot.onlinelearning.service.StudentService;

@RestController
@RequestMapping("api/enrollment")
public class EnrollmentController {
	
	@Autowired
	private EnrollmentService enrollmentService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping("/all")
	public List<Enrollment> getAll(){
		return enrollmentService.getAll();
	}
	
	
	@GetMapping("/student/all")
	public List<Enrollment> getByStudentAll(Principal principal) {
		String username = principal.getName();
		Student student = studentService.getStudentname(username);
		return enrollmentService.getByStudentAll(student);
	}
	
	@GetMapping("/getbyid/{enrollid}")
	public Enrollment getById(@PathVariable int enrollid) {
		return enrollmentService.getById(enrollid); 
	}
	
	@PostMapping("/add")
	public Enrollment addEnrollment(@RequestBody Enrollment enrollment) {
		return enrollmentService.addEnrollment(enrollment);
	}
	
	@PutMapping("/update/{enrollid}")
	public Enrollment updateEnrollment (@PathVariable int enrollid,@RequestBody Enrollment enrollment) {
		Enrollment enrollDB = enrollmentService.getenrollId(enrollid);
		enrollDB.setEnroll_date(enrollment.getEnroll_date());
		enrollDB.setStatus(enrollment.getStatus());
		
		return enrollmentService.updateEnrollment(enrollDB);
		
	}
	
	@DeleteMapping("/delete/{enroll_id}")
	public ResponseEntity<String> deletebyid(@PathVariable int enroll_id) {
		enrollmentService.deletebyid(enroll_id);
		return ResponseEntity.ok("deleted the enrollment id "+enroll_id);
	}
	
	@PostMapping("/assign/{courseid}")
	public Enrollment assignEnrollment(@PathVariable int courseid,@RequestBody Enrollment enrollment,Principal principal ) {
		Course course = courseService.getCourseId(courseid);
		Student student = studentService.getStudentname(principal.getName());
		
		enrollment.setCourse(course);
		enrollment.setStudent(student);
		
		return enrollmentService.assignEnrollment(enrollment);
	}

}
