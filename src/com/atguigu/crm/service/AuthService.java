package com.atguigu.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.crm.entity.Authority;
import com.atguigu.crm.mapper.AuthMapper;

@Service
public class AuthService extends BaseService {
	
	@Autowired
	private AuthMapper authMapper;

	public List<Authority> getParentAuthorities() {
		
		return authMapper.getParentAuthorities();
	}

	public List<Authority> getSubAuthorities(Long id) {
		return authMapper.getSubAuthorities(id);
	}

	public List<Authority> getRoleAuthorities(Integer id) {
		return authMapper.getRoleAuthorities(id);
	}

}
