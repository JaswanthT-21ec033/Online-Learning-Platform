package com.springboot.onlinelearning.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.onlinelearning.enums.CourseCategory;
import com.springboot.onlinelearning.enums.CourseStatus;
import com.springboot.onlinelearning.model.Course;
import com.springboot.onlinelearning.model.Instructor;

public interface CourseRepository extends JpaRepository<Course,Integer> {

	

	List<Course> findByTitle(String coursename);

	List<Course> findByStatus(CourseStatus courseStatus);

	List<Course> findByCategory(CourseCategory category);

	List<Course> findByInstructor(Instructor instructor);
	
	

	

}
