package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;

public interface ReportMapper extends BaseMapper<Object[]>{

	List<Object[]> getContent2(Map<String, Object> mybatisParams);

	long getTotalElements1(Map<String, Object> mybatisParams);

}
