package com.atguigu.crm.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.entity.CustomerDrain;

public interface DrainMapper {

	long getTotalElements(Map<String, Object> myBatisParmas);

	List<CustomerDrain> getContent(Map<String, Object> myBatisParmas);

	CustomerDrain getById(Integer id);

	void delay(@Param("id") Integer id,@Param("delay") String delay);

	void confirm(@Param("id") Long id,@Param("reason") String reason,@Param("confirmDate") Date confirmDate);


}
