package com.springboot.onlinelearning.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import com.springboot.onlinelearning.service.StudentService;
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

import com.springboot.onlinelearning.enums.AssignmentGrade;
import com.springboot.onlinelearning.model.Assignment;
import com.springboot.onlinelearning.model.Course;
import com.springboot.onlinelearning.model.Instructor;
import com.springboot.onlinelearning.model.Student;
import com.springboot.onlinelearning.repository.InstructorRepository;
import com.springboot.onlinelearning.service.AssignmentService;
import com.springboot.onlinelearning.service.CourseService;
import com.springboot.onlinelearning.service.InstructorService;

@RestController
@RequestMapping("api/assignment")
public class AssignmentController {

	@Autowired 
	private AssignmentService assignmentService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private InstructorService instructorService;
	
	
	@GetMapping("/all")
	public List<Assignment> getAll(){
		return assignmentService.getAll();
	}
	@GetMapping("/student/all")
	public List<Assignment> getByStudentAll(Principal principal) {
		String username = principal.getName();
		Student student = studentService.getStudentname(username);
		return assignmentService.getByStudentAll(student);
	}
	
	@GetMapping("/all/{assignid}")
	public Assignment getById(@PathVariable int assignid) {
		return assignmentService.getById(assignid);
	}
	
	
	@PostMapping("/add")
	public Assignment addAssign(@RequestBody Assignment assignment,Principal principal) {
		String username = principal.getName();
		Instructor instructor = instructorService.getname(username);
		assignment.setInstructor(instructor);
		return assignmentService.addAssign(assignment);
	}
	
	@DeleteMapping("/deletebyassignid/{assignId}")
	public ResponseEntity<String> deleteassignById(@PathVariable int assignId) {
		 assignmentService.deleteassignById(assignId);
		return ResponseEntity.ok("Deleted Successfully for Assignment ID "+ assignId);
	}
	
	@GetMapping("/getbyassignmenttitle/{title}")
	public Assignment fetchTitle(@PathVariable String title) {
		return assignmentService.fetchTitle(title);
	}

	
	@GetMapping("/grade/{grade}")
	public List<Assignment> getByGrade(@PathVariable AssignmentGrade  grade){
		return assignmentService.getByGrade(grade);
	}
	
	@PutMapping("/update/{assignid}")
	public Assignment updateByAssignId(@PathVariable int assignid, @RequestBody Assignment assignment,Principal principal) {
		Assignment assignDB = assignmentService.getAssignId(assignid);
		String username = principal.getName();
		Instructor instructor = instructorService.getname(username);
		assignDB.setTitle(assignment.getTitle());
		assignDB.setDescription(assignment.getDescription());
		assignDB.setCreated_date(assignment.getCreated_date());
		assignDB.setDue_date(assignment.getDue_date());
		assignDB.setGrade(assignment.getGrade());
		return assignmentService.updateByAssignId(assignDB);
	}
	
	@PostMapping("/assign/{courseid}/{studid}")
	public Assignment assignCourseInstructor(@PathVariable int courseid, @PathVariable int studid,
			@RequestBody Assignment assignment, Principal principal) {

		Course course = courseService.getCourseId(courseid);
		Student student = studentService.getStudentid(studid);

		String username = principal.getName();
		Instructor instructor = instructorService.getname(username);

		// Set associations
		assignment.setCourse(course);
		assignment.setStudent(student);
		assignment.setInstructor(instructor); 

		return assignmentService.assignCourseInstructor(assignment);
	}



}	
