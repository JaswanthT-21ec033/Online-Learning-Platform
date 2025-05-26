package com.springboot.onlinelearning.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.onlinelearning.model.Instructor;
import com.springboot.onlinelearning.repository.InstructorRepository;

@Service
public class InstructorService {
	
	@Autowired
	private InstructorRepository instructorRepository;

	public Instructor addInstructor(Instructor instructor) {
		
		return instructorRepository.save(instructor);
	}
	
	public Instructor instructorID(int instructorID,Instructor instructor) {
		Optional<Instructor> optional = instructorRepository.findById(instructorID);
		return optional.get();
	}

	public List<Instructor> getAll() {
		
		return instructorRepository.findAll();
	}

	

	public Instructor getById(int instructid) {
		Optional<Instructor> optional = instructorRepository.findById(instructid);
		return optional.get();
	}

	public Instructor updateInstructor(Instructor instructor) {
		return instructorRepository.save(instructor);
	}

	public Instructor getInstructorId(int instructid) {
		Optional<Instructor> optional = instructorRepository.findById(instructid);
		if(optional.isEmpty()) 
			throw new RuntimeException("Invalid Id for the Instructor ID");
		return optional.get();
	}

	public List<Instructor> getByName(String name) {
		
		return instructorRepository.findByName(name);
	}

	

	public Instructor getInstructorByUsername(String username) {
	    return instructorRepository.findByUserUsername(username);
	}

	public Instructor getname(String username) {
		// TODO Auto-generated method stub
		return instructorRepository.findByUserUsername(username);
	}

	public Instructor getByUsername(String username) {
		// TODO Auto-generated method stub
		return instructorRepository.findByUserUsername(username);
	}

	

	 


	

}
