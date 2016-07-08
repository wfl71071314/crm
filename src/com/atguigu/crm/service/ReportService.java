package com.atguigu.crm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.crm.mapper.ReportMapper;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.orm.PropertyFilter;

@Service
public class ReportService extends BaseService<Object[]> {
	
	@Autowired
	private ReportMapper reportMapper;

	public Page<Object[]> getPage2(Integer pageNo, Map<String, Object> params) {
		Page<Object[]> page = new Page<>();
		page.setPageNo(pageNo);
		
		//1. ��ȡ�ܵļ�¼��
		//1.1 �Ѵ���� params תΪ PropertyFilter �ļ���
		List<PropertyFilter> filters = parseHandlerParamsToPropertyFilters(params);
		//1.2 �� RropertyFilter �ļ���תΪ mybatis ���õ� params
		Map<String, Object> mybatisParams = parsePropertyFiltersToMyBatisParmas(filters);
		
		long totalElements = reportMapper.getTotalElements1(mybatisParams);
		page.setTotalElements((int)totalElements);
		
		//2. ��ȡ��ǰҳ��� List
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = page.getPageSize() + fromIndex;
		mybatisParams.put("fromIndex", fromIndex);
		mybatisParams.put("endIndex", endIndex);
		
		List<Object[]> content = reportMapper.getContent2(mybatisParams);
		page.setContent(content);
		
		//3. ��װ Page ������
		return page;
	}

}
