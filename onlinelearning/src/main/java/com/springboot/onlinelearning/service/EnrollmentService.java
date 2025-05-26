package com.springboot.onlinelearning.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.onlinelearning.model.Course;
import com.springboot.onlinelearning.model.Enrollment;
import com.springboot.onlinelearning.model.Instructor;
import com.springboot.onlinelearning.model.Student;
import com.springboot.onlinelearning.repository.CourseRepository;
import com.springboot.onlinelearning.repository.EnrollmentRepository;
import com.springboot.onlinelearning.repository.InstructorRepository;
import com.springboot.onlinelearning.repository.StudentRepository;

@Service
public class EnrollmentService {

    
	
	@Autowired
	private EnrollmentRepository enrollmentRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private InstructorRepository instructorRepository;

	@Autowired
	private CourseRepository courseRepository;

	public List<Enrollment> getByStudentAll(Student student) {
		
		return enrollmentRepository.findByStudent(student);
	}

	public Enrollment addEnrollment(Enrollment enrollment) {
		Course course = enrollment.getCourse();
		Instructor instructor = course.getInstructor();
		instructorRepository.save(instructor);
		courseRepository.save(course);
		enrollment.setCourse(course);
		
		
		Student student = enrollment.getStudent();
		studentRepository.save(student);
		enrollment.setStudent(student);
		
		
		
		
		return enrollmentRepository.save(enrollment);
	}

	public void deletebyid(int enroll_id) {
		
		 enrollmentRepository.deleteById(enroll_id);
	}

	public Enrollment getById(int enrollid) {
		Optional<Enrollment> optional = enrollmentRepository.findById(enrollid);
		return optional.get();
	}

	public Enrollment getenrollId(int enrollid) {
	Optional<Enrollment> optional = enrollmentRepository.findById(enrollid);
			if(optional.isEmpty())
				throw new RuntimeException("invalid ID for the Enrollment");
		return optional.get();
	}

	public Enrollment updateEnrollment(Enrollment enrollment) {
		
		return enrollmentRepository.save(enrollment);
	}

	public Enrollment assignEnrollment(Enrollment enrollment) {
		return enrollmentRepository.save(enrollment);
	}

	public List<Enrollment> getAll() {
	
		return enrollmentRepository.findAll();
	}

	
	
	

}
