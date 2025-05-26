package com.springboot.onlinelearning.service;

import java.util.List;
import java.util.Optional;
import com.springboot.onlinelearning.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.onlinelearning.enums.CourseCategory;
import com.springboot.onlinelearning.enums.CourseStatus;
import com.springboot.onlinelearning.model.Assignment;
import com.springboot.onlinelearning.model.Course;
import com.springboot.onlinelearning.model.Instructor;
import com.springboot.onlinelearning.repository.AssignmentRepository;
import com.springboot.onlinelearning.repository.CourseRepository;

@Service
public class CourseService {

    private final InstructorRepository instructorRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	

    CourseService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

	public List<Course> getAll() {
		return courseRepository.findAll();
	}

	public Course addCourse(Course course) {
//		Instructor instructor = course.getInstructor();
//		instructorRepository.save(instructor);
//		course.setInstructor(instructor);
		return courseRepository.save(course);
	}

	public Course getCourseId(int courseid) {
		Optional<Course> optional = courseRepository.findById(courseid);
		if(optional.isEmpty())
			throw new RuntimeException("Course Id is Invalid");
		
		return optional.get();
	}

	public Course updateByCourseId(Course course) {
		
		return courseRepository.save(course);
	}
	
	public Course getByCourseId(int courseid){
		Optional <Course> optional = courseRepository.findById(courseid);
		return optional.get();
	}

	public List<Course> getByCourseName(String coursename) {
		return  courseRepository.findByTitle(coursename);
	}

	public Course assignInstructor(Course course) {
		
		return courseRepository.save(course);
	}

	public List<Course> getByActive(CourseStatus courseStatus) {
		
		return courseRepository.findByStatus(courseStatus);
	}

	public List<Course> getByInactive(CourseStatus courseStatus) {
		
		return courseRepository.findByStatus(courseStatus);
	}

	public List<Course> getByCategory(CourseCategory category) {
		
		return courseRepository.findByCategory(category);
	}

	public List<Course> getByInstructorAll(Instructor instructor) {
		// TODO Auto-generated method stub
		return courseRepository.findByInstructor(instructor);
	}

	public Course findById(int courseid) {
		Optional<Course> optional = courseRepository.findById(courseid);
		if(optional.isEmpty()){
			throw new RuntimeException("Invalid Id for the Course");
		}
		return optional.get();
	}

	public Course unassignInstructor(Course course) {
		
		return courseRepository.save(course);
	}

	

	



	
	
	

}
