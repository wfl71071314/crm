package com.atguigu.crm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.crm.entity.Product;
import com.atguigu.crm.mapper.ProductMapper;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.orm.PropertyFilter;

@Service
public class ProductService extends BaseService {

	@Autowired
	private ProductMapper productMapper;

	public Page<Product> getPage2(Integer pageNo) {
		Page<Product> page=new Page<>();
		page.setPageNo(pageNo);
		
		long totalElements=productMapper.getTotalElements2();
		page.setTotalElements((int)totalElements);
		
		int fromIndex=(page.getPageNo()-1)*page.getPageSize()+1;
		int endIndex=page.getPageSize()+fromIndex;
		
		List<Product> content=productMapper.getList2(fromIndex,endIndex);
		
		page.setContent(content);
		
		return page;
	}

	public void delete(Integer id) {
		productMapper.delete(id);
	}

	public Product getById(Integer id) {
		return productMapper.getById(id);
	}

	public void save(Product product) {
		productMapper.save(product);
	}

	public void update(Product product) {
		productMapper.update(product);
	}

	public Page<Product> getPage2(Integer pageNo, Map<String, Object> params) {
		Page<Product> page=new Page<>();
		page.setPageNo(pageNo);
		List<PropertyFilter> filters=parseHandlerParamsToPropertyFilters(params);
		
		Map<String,Object> mybatisParams=parsePropertyFiltersToMyBatisParmas(filters);
		
		long totalElements=productMapper.getTotalElements(mybatisParams);
		page.setTotalElements((int)totalElements);
		
		int fromIndex=(page.getPageNo()-1)*page.getPageSize()+1;
		int endIndex=page.getPageSize()+fromIndex;
		
		mybatisParams.put("fromIndex", fromIndex);
		mybatisParams.put("endIndex", endIndex);
		List<Product> content=productMapper.getContent(mybatisParams);
		
		page.setContent(content);
		
		return page;
	}

	public List<Product> getAll() {
		return productMapper.getAll();
	}
}
