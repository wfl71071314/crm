/**
 * 
 */
package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.entity.User;

/**
 * @author wang fu liang 
 *
 */
public interface UserMapper {
	public User getByName(@Param("name") String name);

	public long getTotalElements(Map<String, Object> myBatisParmas);

	public List<User> getContent(Map<String, Object> myBatisParmas);

	public User getById(Integer id);

	public void save(User user);

	public void update(User user);

	public void delete(Integer id);

	public List<User> getAll();
}
