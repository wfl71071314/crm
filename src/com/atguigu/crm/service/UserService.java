/**
 * 
 */
package com.atguigu.crm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.crm.entity.User;
import com.atguigu.crm.mapper.UserMapper;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.orm.PropertyFilter;

/**
 * @author wang fu liang 
 *
 */
@Service
public class UserService extends BaseService{
	
	@Autowired
	private UserMapper userMapper;
	
	public User getByName(String name,String password){
		
		User user=userMapper.getByName(name);
		if(user!=null && user.getEnabled() == 1
				&& user.getPassword().equals(password)){
			return user;
		}
		return null;
	}

	public Page<User> getList(Integer pageNo, Map<String, Object> params) {
		Page<User> page=new Page<>();
		page.setPageNo(pageNo);
		
		List<PropertyFilter> filters = parseHandlerParamsToPropertyFilters(params);
		
		Map<String, Object> myBatisParmas = parsePropertyFiltersToMyBatisParmas(filters);
		
		long totalElements=userMapper.getTotalElements(myBatisParmas);
		
		page.setTotalElements((int)totalElements);
		
		int fromIndex=(page.getPageNo()-1)*page.getPageSize()+1;
		int endIndex=page.getPageSize()+fromIndex;
		
		myBatisParmas.put("fromIndex", fromIndex);
		myBatisParmas.put("endIndex", endIndex);
		
		List<User> content=userMapper.getContent(myBatisParmas);
		
		page.setContent(content);
		
		return page;
	}

	public User getById(Integer id) {
		return userMapper.getById(id);
	}

	public void save(User user) {
		userMapper.save(user);
	}

	public void update(User user) {
		userMapper.update(user);
	}

	public void delete(Integer id) {
		userMapper.delete(id);
	}

	public User getByName(String name) {
		return userMapper.getByName(name);
	}

	public List<User> getAll() {
		return userMapper.getAll();
	}
}
