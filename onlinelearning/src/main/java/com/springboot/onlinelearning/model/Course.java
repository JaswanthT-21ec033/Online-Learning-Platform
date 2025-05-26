package com.springboot.onlinelearning.model;

import java.sql.Date;

import com.springboot.onlinelearning.enums.CourseCategory;
import com.springboot.onlinelearning.enums.CourseStatus;

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
@Table(name = "course")
public class Course {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int course_id;
	
	@Column(nullable = false)
	private String title;

	private String description;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CourseStatus status;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CourseCategory category;
	
	@Column(nullable = false)
	private double price;
	
	@Column(nullable = false)
	private Date start_At;
	
	@Column(nullable = false)
	private Date end_At;
	
	@ManyToOne
	private Instructor instructor;

	

	public int getCourse_id() {
		return course_id;
	}

	public void setCourse_id(int course_id) {
		this.course_id = course_id;
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

	public CourseStatus getStatus() {
		return status;
	}

	public void setStatus(CourseStatus status) {
		this.status = status;
	}

	public CourseCategory getCategory() {
		return category;
	}

	public void setCategory(CourseCategory category) {
		this.category = category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getStart_At() {
		return start_At;
	}

	public void setStart_At(Date start_At) {
		this.start_At = start_At;
	}

	public Date getEnd_At() {
		return end_At;
	}

	public void setEnd_At(Date end_At) {
		this.end_At = end_At;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}
	
	
}
