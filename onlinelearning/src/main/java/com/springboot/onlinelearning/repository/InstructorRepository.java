package com.springboot.onlinelearning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.onlinelearning.model.Instructor;

public interface InstructorRepository extends JpaRepository<Instructor, Integer>{

	List<Instructor> findByName(String name);

	Instructor findByUserUsername(String username);

	

	


}
