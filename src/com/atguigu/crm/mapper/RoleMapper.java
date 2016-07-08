package com.atguigu.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.entity.Role;

public interface RoleMapper {

	long getTotalElement();

	List<Role> getList(@Param("fromIndex") int fromIndex,@Param("endIndex") int endIndex);

	Role getById(Integer id);

	void save(Role role);

	void update(Role role);

	void delete(Integer id);
	
	List<Role> getAll();

	void deleteAuth(Long id);

	void batchSave(Role role);

}
