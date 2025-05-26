package com.springboot.onlinelearning.model;

import java.sql.Date;

import com.springboot.onlinelearning.enums.EnrollmentStatus;

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
@Table(name = "enrollment")
public class Enrollment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int enroll_id;
	
	@Column(nullable = false)
	private Date enroll_date;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EnrollmentStatus status;
	
	@ManyToOne
	private Course course;
	
	@ManyToOne
	private Student student;
	

	public int getEnroll_id() {
		return enroll_id;
	}

	public void setEnroll_id(int enroll_id) {
		this.enroll_id = enroll_id;
	}

	
	public Date getEnroll_date() {
		return enroll_date;
	}

	public void setEnroll_date(Date enroll_date) {
		this.enroll_date = enroll_date;
	}

	public EnrollmentStatus getStatus() {
		return status;
	}

	public void setStatus(EnrollmentStatus status) {
		this.status = status;
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
	
	
}
