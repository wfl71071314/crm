package com.atguigu.crm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.entity.Customer;
import com.atguigu.crm.entity.Dict;
import com.atguigu.crm.entity.SalesChance;
import com.atguigu.crm.mapper.CustomerMapper;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.orm.PropertyFilter;
import com.atguigu.crm.orm.PropertyFilter.MatchType;

@Service
public class CustomerServices {

	@Autowired
	private CustomerMapper customerMapper;
	
	public Page<Customer> getPage(int pageNo, Map<String, Object> params) {
		Page<Customer> page = new Page<>();
		page.setPageNo(pageNo);
		
		//1. 获取总的记录数
		//1.1 把传入的 params 转为 PropertyFilter 的集合
		List<PropertyFilter> filters = parseHandlerParamsToPropertyFilters(params);
		//1.2 把 RropertyFilter 的集合转为 mybatis 可用的 params
		Map<String, Object> mybatisParams = parsePropertyFiltersToMyBatisParmas(filters);
		
		System.out.println("---------------------"+mybatisParams.get("level"));
		
		long totalElements = customerMapper.getTotalElements2(mybatisParams);
		
		page.setTotalElements((int)totalElements);
		
		//2. 获取当前页面的 List
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = page.getPageSize() + fromIndex;
		mybatisParams.put("fromIndex", fromIndex);
		mybatisParams.put("endIndex", endIndex);
		
		List<Customer> content = customerMapper.getContent2(mybatisParams);
		page.setContent(content);
		
		//3. 组装 Page 并返回
		return page;
	}
	
	static{
		DateConverter dateConverter = new DateConverter();
		dateConverter.setPatterns(new String[]{"yyyy-MM-dd","yyyy-MM-dd hh:mm:ss"});
		ConvertUtils.register(dateConverter, Date.class);
	}
	
	private Map<String, Object> parsePropertyFiltersToMyBatisParmas(
			List<PropertyFilter> filters) {
		Map<String, Object> params = new HashMap<String, Object>();
		for(PropertyFilter filter: filters){
			String propertyName = filter.getPropertyName();
			
			Object propertyVal = filter.getPropertyVal();
			Class propertyType = filter.getPropertyType();
			propertyVal = ConvertUtils.convert(propertyVal, propertyType);
			
			MatchType matchType = filter.getMatchType();
			if(matchType == MatchType.LIKE&&propertyVal!=""){
				propertyVal = "%" + propertyVal + "%";
			}
			if(matchType == MatchType.LIKE&&propertyVal==""){
				propertyVal = null;
			}
			if(matchType==MatchType.EQ&&propertyVal==""){
				propertyVal=null;
			}
			
			params.put(propertyName, propertyVal);
		}
		
		return params;
	}

	private List<PropertyFilter> parseHandlerParamsToPropertyFilters(
			Map<String, Object> params) {
		List<PropertyFilter> filters = new ArrayList<>();
		
		for(Map.Entry<String, Object> entry: params.entrySet()){
			String fieldName = entry.getKey();
			Object fieldVal = entry.getValue();
			
			PropertyFilter filter = new PropertyFilter(fieldName, fieldVal);
			filters.add(filter);
		}
		
		return filters;
	}
	public Page<Customer> getPage(Integer pageNo) {
		Page<Customer> page=new Page<>();
		page.setPageNo(pageNo);
		
		long totalElements=customerMapper.getTotalElement();
		
		page.setTotalElements((int)totalElements);
		
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = page.getPageSize() + fromIndex;
		
		List<Customer> content=customerMapper.getList(fromIndex,endIndex);
		
		page.setContent(content);
		
		return page;
	}

	public List<String> getRegions(String type) {
		return customerMapper.getRegions(type);
	}

	public List<String> getLevels(String type) {
		return customerMapper.getLevels(type);
	}

	public void confirm(Long id) {
		customerMapper.comfirm(id);
	}

	public List<Customer> getAll() {
		return customerMapper.getAll();
	}
	
}
