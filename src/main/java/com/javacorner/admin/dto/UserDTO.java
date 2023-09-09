package com.javacorner.admin.dto;

import java.util.HashSet;
import java.util.Set;

import com.javacorner.admin.entity.Instructor;
import com.javacorner.admin.entity.Role;
import com.javacorner.admin.entity.Student;

public class UserDTO {
	
	private Long userId;
	
	private String email;
	
	private String password;
	
	private Set<Role> roles = new HashSet<>();
	
	private Student student;
	
	private Instructor instructor;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
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
