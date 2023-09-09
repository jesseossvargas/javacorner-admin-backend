package com.javacorner.admin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javacorner.admin.dao.RoleDao;
import com.javacorner.admin.entity.Role;
import com.javacorner.admin.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{
	
	private RoleDao roleDao;
	
	public RoleServiceImpl(RoleDao roleDao) {
		super();
		this.roleDao = roleDao;
	}

	@Override
	public Role createRole(String roleName) {
		return roleDao.save(new Role(roleName));
	}

}
