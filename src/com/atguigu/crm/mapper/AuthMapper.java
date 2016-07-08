package com.atguigu.crm.mapper;

import java.util.List;

import com.atguigu.crm.entity.Authority;

public interface AuthMapper {

	List<Authority> getParentAuthorities();

	List<Authority> getSubAuthorities(Long id);

	List<Authority> getRoleAuthorities(Integer id);

}
