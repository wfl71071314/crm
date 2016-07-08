package com.atguigu.crm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.crm.entity.Dict;
import com.atguigu.crm.entity.Storage;
import com.atguigu.crm.mapper.StorageMapper;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.orm.PropertyFilter;

@Service
public class StorageService extends BaseService {

	@Autowired
	private StorageMapper storageMapper;

	public Page<Storage> getPage2(Integer pageNo) {
		Page<Storage> page=new Page<>();
		page.setPageNo(pageNo);
		
		long totalElements=storageMapper.getTotalElements2();
		page.setTotalElements((int)totalElements);
		
		int fromIndex=(page.getPageNo()-1)*page.getPageSize()+1;
		int endIndex=page.getPageSize()+fromIndex;
		List<Storage> content=storageMapper.getPage2(fromIndex,endIndex);
		
		page.setContent(content);
		return page;
	}

	public Storage getById(Integer id) {
		return storageMapper.getById(id);
	}

	public void save(Storage storage) {
		storageMapper.save(storage);
	}

	public void update(Storage storage) {
		storageMapper.update(storage);
	}

	public void delete(Integer id) {
		storageMapper.delete(id);
	}

	public Page<Storage> getPage(Integer pageNo, Map<String, Object> params) {
		Page<Storage> page = new Page<>();
		page.setPageNo(pageNo);
		
		//1. 获取总的记录数
		//1.1 把传入的 params 转为 PropertyFilter 的集合
		List<PropertyFilter> filters = parseHandlerParamsToPropertyFilters(params);
		//1.2 把 RropertyFilter 的集合转为 mybatis 可用的 params
		Map<String, Object> mybatisParams = parsePropertyFiltersToMyBatisParmas(filters);
		
		long totalElements = storageMapper.getTotalElements(mybatisParams);
		page.setTotalElements((int)totalElements);
		
		//2. 获取当前页面的 List
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = page.getPageSize() + fromIndex;
		mybatisParams.put("fromIndex", fromIndex);
		mybatisParams.put("endIndex", endIndex);
		
		List<Storage> content = storageMapper.getContent2(mybatisParams);
		page.setContent(content);
		
		//3. 组装 Page 并返回
		return page;
	}
}
