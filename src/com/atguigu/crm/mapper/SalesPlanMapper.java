package com.atguigu.crm.mapper;


import java.util.Date;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.entity.SalesPlan;

public interface SalesPlanMapper extends BaseMapper<SalesPlan>{

	Set<SalesPlan> getById(Integer id);

	void update(@Param("id") Integer id,@Param("todo") String todo);

	void delete(Integer id);

	void save(@Param("id") Integer id,@Param("date") Date date,@Param("todo") String todo);

	void saveResult(@Param("id") Integer id,@Param("result") String result);

}
