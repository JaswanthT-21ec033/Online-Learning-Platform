package com.springboot.onlinelearning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.onlinelearning.model.Enrollment;
import com.springboot.onlinelearning.model.Student;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer>{

	List<Enrollment> findByStudent(Student student);

}
