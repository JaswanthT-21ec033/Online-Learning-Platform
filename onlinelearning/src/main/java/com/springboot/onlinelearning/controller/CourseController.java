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

import com.springboot.onlinelearning.enums.CourseCategory;
import com.springboot.onlinelearning.enums.CourseStatus;
import com.springboot.onlinelearning.model.Course;
import com.springboot.onlinelearning.model.Instructor;
import com.springboot.onlinelearning.model.Student;
import com.springboot.onlinelearning.service.CourseService;
import com.springboot.onlinelearning.service.InstructorService;

@RestController
@RequestMapping("/api/course")
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private InstructorService instructorService;
	
	
	@GetMapping("/all")
	public List<Course> getAll() {
		return courseService.getAll();
	}
	
	@GetMapping("/instructor/all")
		public List<Course> getByInstructorAll(Principal principal){
			String username = principal.getName();
			Instructor instructor = instructorService.getname(username);
			return courseService.getByInstructorAll(instructor);	
		}
	
	
	@GetMapping("/getbyid/{courseid}")
	public Course getByCourseId(@PathVariable int courseid){
		return courseService.getByCourseId(courseid);
		
	}
	
	@GetMapping("/getbycoursename/{coursename}")
	public List<Course> getByCourseName(@PathVariable String coursename) {
		return courseService.getByCourseName(coursename);
		
	}
	
	@PostMapping("/add")
	public Course addCourse(@RequestBody Course course) {
		return courseService.addCourse(course);
	}
	
	@PutMapping("/update/{courseid}")
	public Course updateByCourseId(@PathVariable int courseid, @RequestBody Course course, Principal principal) {
	    Course courseDB = courseService.getCourseId(courseid);
	    String username = principal.getName();
	    Instructor instructor = instructorService.getname(username);

	    

	    courseDB.setTitle(course.getTitle());
	    courseDB.setDescription(course.getDescription());
	    courseDB.setStatus(course.getStatus());
	    courseDB.setCategory(course.getCategory());
	    courseDB.setPrice(course.getPrice());
	    courseDB.setStart_At(course.getStart_At());
	    courseDB.setEnd_At(course.getEnd_At());
	    courseDB.setInstructor(instructor); // Optional but safe

	    return courseService.updateByCourseId(courseDB);
	}

	
	@PostMapping("/assign/{courseid}")
	public Course assignInstructor(Principal principal,@PathVariable int courseid ) {
	    String username = principal.getName(); 
	    Instructor instructor = instructorService.getInstructorByUsername(username); 
	    Course course = courseService.findById(courseid);
	    course.setInstructor(instructor);                                            
	    return courseService.assignInstructor(course);                               
	}


	
	
	@GetMapping("/active")
	public List<Course> getByActive () {
		return courseService.getByActive(CourseStatus.ACTIVE);
	}
	
	@GetMapping("/inactive")
	public List<Course> getByInactive(){
		return courseService.getByInactive(CourseStatus.INACTIVE);
	}
	
	@GetMapping("/{category}")
	public List<Course> getByCategory(@PathVariable CourseCategory category){
		return courseService.getByCategory(category);
	}
	
	@PutMapping("/unassign/{courseid}")
	public Course unassignInstructor(Principal principal,@PathVariable int courseid ) {
		 String username = principal.getName(); 
		    Instructor instructor = instructorService.getInstructorByUsername(username); 
		    Course course = courseService.findById(courseid);
		    course.setInstructor(null);         
		return courseService.unassignInstructor(course);
	}
	
	
	
	

}
