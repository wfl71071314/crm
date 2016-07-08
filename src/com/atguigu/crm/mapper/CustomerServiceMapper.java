package com.atguigu.crm.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.entity.Customer;
import com.atguigu.crm.entity.CustomerService;


public interface CustomerServiceMapper extends BaseMapper<CustomerService> {

	void create(CustomerService customerService);

	List<Customer> getAll();

	void delete(Long id);


	void allot(@Param("id") Integer id,@Param("allotId") Integer allotId,@Param("allotDate") Date allotDate);

	CustomerService getById(Integer id);

	void deal(@Param("id") Long id,@Param("serviceDeal") String serviceDeal, 
			@Param("dealDate") Date dealDate,@Param("serviceState") String serviceState);

	void feedBack(@Param("id") Long id,@Param("dealResult") String dealResult,@Param("satisfy") String satisfy);

	
}
