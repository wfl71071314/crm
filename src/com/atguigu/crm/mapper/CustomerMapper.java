package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.entity.Customer;
import com.atguigu.crm.entity.SalesChance;

public interface CustomerMapper {

	long getTotalElement();

	List<Customer> getList(@Param("fromIndex") int fromIndex,@Param("endIndex") int endIndex);

	List<String> getRegions(@Param("type") String type);

	List<String> getLevels(@Param("type") String type);

	long getTotalElements2(Map<String, Object> mybatisParams);

	List<Customer> getContent2(Map<String, Object> mybatisParams);

	void comfirm(Long id);

	List<Customer> getAll();
	
}
