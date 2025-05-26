package com.springboot.onlinelearning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.onlinelearning.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>{

	List<Student> findByName(String name);

	Student findByUserUsername(String name);

	

}
