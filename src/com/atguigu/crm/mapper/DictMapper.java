package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.entity.Dict;

public interface DictMapper {


	long getTotalElements();

	List<Dict> getPage2(@Param("fromIndex") int fromIndex,@Param("endIndex") int endIndex);

	long getTotalElements2(Map<String, Object> mybatisParams);

	List<Dict> getContent2(Map<String, Object> mybatisParams);

	void save(Dict dict);

	void delete(Integer id);

	Dict getById(Integer id);

	void update(Dict dict);

	List<String> getByType(String type);

}
