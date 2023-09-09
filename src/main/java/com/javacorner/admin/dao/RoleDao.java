package com.javacorner.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javacorner.admin.entity.Role;

public interface RoleDao extends JpaRepository<Role, Long>{
	
	Role findByName(String name);

}
