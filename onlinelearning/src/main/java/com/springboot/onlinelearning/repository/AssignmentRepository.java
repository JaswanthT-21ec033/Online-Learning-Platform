package com.springboot.onlinelearning.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.onlinelearning.enums.AssignmentGrade;
import com.springboot.onlinelearning.model.Assignment;
import com.springboot.onlinelearning.model.Student;

public interface AssignmentRepository extends JpaRepository<Assignment, Integer>{

	Assignment findByTitle(String title);

	List<Assignment> findByGrade(AssignmentGrade Grade);

	List<Assignment> findByStudent(Student student);


}
