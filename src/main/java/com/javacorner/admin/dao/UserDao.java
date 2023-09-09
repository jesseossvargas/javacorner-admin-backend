package com.javacorner.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javacorner.admin.entity.User;

public interface UserDao extends JpaRepository<User, Long>{
	
	User findByEmail(String email);
	
}
