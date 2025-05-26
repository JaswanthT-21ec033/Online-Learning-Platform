package com.springboot.onlinelearning.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.onlinelearning.model.Student;
import com.springboot.onlinelearning.model.User;
import com.springboot.onlinelearning.repository.AuthRepository;
import com.springboot.onlinelearning.repository.StudentRepository;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired 
	private AuthRepository authRepository;

	public List<Student> getAll() {
		
		return studentRepository.findAll();
	}

	public Student addStudent(Student student) {
		
		return studentRepository.save(student);
	}

	public Student getStudentid(int studid) {
		Optional<Student> optional = studentRepository.findById(studid);
		if(optional.isEmpty()) 
			throw new RuntimeException("Invalid Id for the Student");
		
		
		return optional.get();
	}

	public Student updateStudent(Student student) {
		
		return studentRepository.save(student);
	}

	public List<Student> getByName(String name) {
		
		return studentRepository.findByName(name);
	}

	public Student getStudentname(String name) {
		// TODO Auto-generated method stub
		return studentRepository.findByUserUsername(name);
	}

	

	

	

	

}
