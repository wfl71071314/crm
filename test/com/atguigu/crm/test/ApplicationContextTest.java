package com.atguigu.crm.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.atguigu.crm.entity.SalesChance;
import com.atguigu.crm.entity.User;
import com.atguigu.crm.mapper.SalesChanceMapper;
import com.atguigu.crm.mapper.UserMapper;

public class ApplicationContextTest {

	private ApplicationContext ctx = null;
	private UserMapper userMapper = null;
	private SalesChanceMapper salesChanceMapper = null;
	
	{
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		userMapper = ctx.getBean(UserMapper.class);
		salesChanceMapper = ctx.getBean(SalesChanceMapper.class);
	}
	
	@Test
	public void testSalesChanceMapperGetTotalElements2(){
		Map<String, Object> params = new HashMap<String, Object>();
		User createBy = new User();
		createBy.setId(24L);
		params.put("createBy", createBy);
		
		int status = 1;
		params.put("status", status);
		
		params.put("custName", "%±±%");
		
		long result = salesChanceMapper.getTotalElements2(params);
		System.out.println(result);
	}
	
	@Test
	public void testSalesChanceMapperGetTotalElements(){
		User createBy = new User();
		createBy.setId(24L);
		int status = 1;
		
		long result = salesChanceMapper.getTotalElements(createBy, status);
		System.out.println(result);
	}
	
	@Test
	public void testUserMapper(){
		User user = userMapper.getByName("admin");
		System.out.println(user.getPassword());
		System.out.println(user.getRole().getName());
	}
	
	@Test
	public void testDataSource() throws SQLException {
		DataSource dataSource = ctx.getBean(DataSource.class);
		Connection connection = dataSource.getConnection();
		
		System.out.println(connection);
	}

}
