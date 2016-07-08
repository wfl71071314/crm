package com.atguigu.crm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.crm.entity.Dict;
import com.atguigu.crm.mapper.DictMapper;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.orm.PropertyFilter;

@Service
public class DictService extends BaseService<Dict>{
	@Autowired
	private DictMapper dictMapper ;

	public Page<Dict> getPage2(Integer pageNo) {
		Page<Dict> page=new Page<>();
		page.setPageNo(pageNo);
		
		long totalElements=dictMapper.getTotalElements();
		
		page.setTotalElements((int)totalElements);
		
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = page.getPageSize() + fromIndex;
		
		List<Dict> content=dictMapper.getPage2(fromIndex,endIndex);
		
		page.setContent(content);
		
		return page;
	}

	public Page<Dict> getPage(Integer pageNo, Map<String, Object> params) {
		Page<Dict> page = new Page<>();
		page.setPageNo(pageNo);
		
		//1. 获取总的记录数
		//1.1 把传入的 params 转为 PropertyFilter 的集合
		List<PropertyFilter> filters = parseHandlerParamsToPropertyFilters(params);
		//1.2 把 RropertyFilter 的集合转为 mybatis 可用的 params
		Map<String, Object> mybatisParams = parsePropertyFiltersToMyBatisParmas(filters);
		
		long totalElements = dictMapper.getTotalElements2(mybatisParams);
		page.setTotalElements((int)totalElements);
		
		//2. 获取当前页面的 List
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = page.getPageSize() + fromIndex;
		mybatisParams.put("fromIndex", fromIndex);
		mybatisParams.put("endIndex", endIndex);
		
		List<Dict> content = dictMapper.getContent2(mybatisParams);
		page.setContent(content);
		
		//3. 组装 Page 并返回
		return page;
	}

	public void save(Dict dict) {
		dictMapper.save(dict);
	}

	public void delete(Integer id) {
		dictMapper.delete(id);
	}

	public Dict getById(Integer id) {
		return dictMapper.getById(id);
	}

	public void update(Dict dict) {
		dictMapper.update(dict);
	}

	public List<String> getByType(String type) {
		return dictMapper.getByType(type);
	}
}
