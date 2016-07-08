package com.atguigu.crm.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.crm.entity.CustomerDrain;
import com.atguigu.crm.mapper.DrainMapper;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.orm.PropertyFilter;

@Service
public class DrainService extends BaseService {

	@Autowired
	private DrainMapper drainMapper;

	public Page<CustomerDrain> getPage(Integer pageNo,
			Map<String, Object> params) {
		Page<CustomerDrain> page=new Page<>();
		page.setPageNo(pageNo);
		
		List<PropertyFilter> filters = parseHandlerParamsToPropertyFilters(params);
		Map<String, Object> myBatisParmas = parsePropertyFiltersToMyBatisParmas(filters);
		
		
		long totalElements=drainMapper.getTotalElements(myBatisParmas);
		
		page.setTotalElements((int)totalElements);
		
		int fromIndex=(page.getPageNo()-1)*page.getPageSize()+1;
		int endIndex=page.getPageSize()+fromIndex;
		myBatisParmas.put("fromIndex", fromIndex);
		myBatisParmas.put("endIndex", endIndex);
		
		List<CustomerDrain> content=drainMapper.getContent(myBatisParmas);
		
		page.setContent(content);
		
		return page;
	}

	public CustomerDrain getById(Integer id) {
		return drainMapper.getById(id);
	}

	public void delay(Integer id, String delay) {
		drainMapper.delay(id,delay);
	}

	public void confirm(Long id, String reason, Date confirmDate) {
		drainMapper.confirm(id,reason,confirmDate);
	}
}

