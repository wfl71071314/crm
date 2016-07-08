package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.entity.SalesChance;
import com.atguigu.crm.entity.User;

public interface SalesChanceMapper {
	
	/**
	 * �ж����ѯ����, �������Բ�ȡ���µ� 3 �ַ�ʽ
	 * 1. ʹ�� @Param ע�������һ��ӳ��. �鷳
	 * 2. ����һ�����Ƶ� bean. ��Ҫ���ⶨ�� bean ����. 
	 * 3. ����һ�� Map
	 */
	long getTotalElements2(Map<String, Object> params);

	List<SalesChance> getContent2(Map<String, Object> params);
	
	void update(SalesChance chance);
	
	SalesChance getById(@Param("id") Integer id);
	
	void delete(@Param("id") Integer id);

	void save(SalesChance chance);
	
	long getTotalElements(@Param("createBy") User createBy, 
			@Param("status") int status);

	List<SalesChance> getContent(@Param("createBy") User createBy, @Param("status") int status, 
			@Param("fromIndex") int fromIndex,
			@Param("endIndex") int endIndex);

	void dispatch(SalesChance salesChance);
	
	
	
}
