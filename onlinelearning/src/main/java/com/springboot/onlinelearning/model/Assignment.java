package com.springboot.onlinelearning.model;

import java.sql.Date;

import com.springboot.onlinelearning.enums.AssignmentGrade;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "assignment")
public class Assignment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int assign_id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String description;
	
	private Date created_date;
	
	@Column(nullable = false)
	private Date due_date;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AssignmentGrade grade;
	
	@ManyToOne 
	private Course course;
	
	@ManyToOne
	private Student student;
	
	@ManyToOne
	private Instructor instructor;

	public int getAssign_id() {
		return assign_id;
	}

	public void setAssign_id(int assign_id) {
		this.assign_id = assign_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public Date getDue_date() {
		return due_date;
	}

	public void setDue_date(Date due_date) {
		this.due_date = due_date;
	}

	public AssignmentGrade getGrade() {
		return grade;
	}

	public void setGrade(AssignmentGrade grade) {
		this.grade = grade;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	
	
	
	
	
	
	
}
