package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.entity.Storage;

public interface StorageMapper {

	long getTotalElements2();

	List<Storage> getPage2(@Param("fromIndex") int fromIndex,@Param("endIndex") int endIndex);

	Storage getById(Integer id);

	void save(Storage storage);

	void update(Storage storage);

	void delete(Integer id);

	long getTotalElements(Map<String, Object> mybatisParams);

	List<Storage> getContent2(Map<String, Object> mybatisParams);

}
