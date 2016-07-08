package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.entity.Product;

public interface ProductMapper {

	long getTotalElements2();

	List<Product> getList2(@Param("fromIndex") int fromIndex,@Param("endIndex") int endIndex);

	void delete(Integer id);

	Product getById(Integer id);

	void save(Product product);

	void update(Product product);

	long getTotalElements(Map<String, Object> mybatisParams);

	List<Product> getContent(Map<String, Object> mybatisParams);

	List<Product> getAll();
	
}
