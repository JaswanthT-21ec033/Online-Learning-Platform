package com.springboot.onlinelearning.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.onlinelearning.enums.AssignmentGrade;
import com.springboot.onlinelearning.model.Assignment;
import com.springboot.onlinelearning.model.Course;
import com.springboot.onlinelearning.model.Instructor;
import com.springboot.onlinelearning.model.Student;
import com.springboot.onlinelearning.repository.AssignmentRepository;
import com.springboot.onlinelearning.repository.CourseRepository;
import com.springboot.onlinelearning.repository.InstructorRepository;
@Service 
public class AssignmentService {
	
	@Autowired
	private AssignmentRepository assignmentRepository;
	
	@Autowired
	private InstructorRepository instructorRepository;
	
	@Autowired
	private CourseRepository courseRepository;

	public List<Assignment> getByStudentAll(Student student) {
		
		return assignmentRepository.findByStudent(student);
	}

	public Assignment getById(int assignid) {
		Optional<Assignment> optional = assignmentRepository.findById(assignid);
		return optional.get();
	}

	public Assignment addAssign(Assignment assignment) {
		Course course = assignment.getCourse();
		Instructor instructor = course.getInstructor();
		instructorRepository.save(instructor);
		course.setInstructor(instructor);
		courseRepository.save(course);
		assignment.setCourse(course);
		return assignmentRepository.save(assignment);
	}

	public void deleteassignById(int assignId) {
		
		 assignmentRepository.deleteById(assignId);
	}

	public Assignment fetchTitle(String title) {
		return assignmentRepository.findByTitle(title);
	}



	public List<Assignment> getByGrade(AssignmentGrade grade) {
		return assignmentRepository.findByGrade(grade);
	}

	public Assignment getAssignId(int assignid) {
		Optional<Assignment> optional = assignmentRepository.findById(assignid);
		if(optional.isEmpty()) 
			throw new RuntimeException("AssignID is invalid");
		return optional.get();
	}

	public Assignment updateByAssignId(Assignment assignment) {
		return assignmentRepository.save(assignment);
	}

	public Assignment assignCourseInstructor(Assignment assignment) {
		
		return assignmentRepository.save(assignment);
	}

	public List<Assignment> getAll() {
		
		return assignmentRepository.findAll();
	}

	

}
