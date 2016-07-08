package com.atguigu.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.entity.Role;
import com.atguigu.crm.mapper.RoleMapper;
import com.atguigu.crm.orm.Page;


@Service
public class RoleService extends BaseService {

	@Autowired
	private RoleMapper roleMapper;

	public Page<Role> getPage(Integer pageNo) {
		Page<Role> page=new Page<>();
		page.setPageNo(pageNo);
		
		long totalElements=roleMapper.getTotalElement();
		
		page.setTotalElements((int)totalElements);
		
		int fromIndex=(page.getPageNo()-1)*page.getPageSize()+1;
		int endIndex=page.getPageSize()+fromIndex;
		
		List<Role> content=roleMapper.getList(fromIndex,endIndex);
		
		page.setContent(content);
		
		return page;
	}

	public Role getById(Integer id) {
		return roleMapper.getById(id);
	}

	public void save(Role role) {
		roleMapper.save(role);
	}

	public void update(Role role) {
		roleMapper.update(role);
	}

	public void delete(Integer id) {
		roleMapper.delete(id);
	}

	public List<Role> getAll() {
		return roleMapper.getAll();
	}

	@Transactional
	public void batchSave(Role role) {
		roleMapper.deleteAuth(role.getId());
		roleMapper.batchSave(role);
	}
}
