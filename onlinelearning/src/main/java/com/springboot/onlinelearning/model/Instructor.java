package com.springboot.onlinelearning.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="instructor")
public class Instructor {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int instruct_id;
	
	@Column(nullable = false)
	private String name;
	
	private String email;
	
	@Column(nullable = false)
	private long contact;
	
	@Column(nullable = false)
	private LocalDate joined_At;
	
	private String bio;
	
	@OneToOne
	private User user;
	
	public int getInstruct_id() {
		return instruct_id;
	}

	public void setInstruct_id(int instruct_id) {
		this.instruct_id = instruct_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getContact() {
		return contact;
	}

	public void setContact(long contact) {
		this.contact = contact;
	}

	public LocalDate getJoined_At() {
		return joined_At;
	}

	public void setJoined_At(LocalDate joined_At) {
		this.joined_At = joined_At;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	

}
