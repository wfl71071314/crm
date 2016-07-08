package com.atguigu.crm.service;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.entity.SalesPlan;
import com.atguigu.crm.mapper.SalesPlanMapper;

@Service
public class SalesPlanService extends BaseService<SalesPlan>{
	
	@Autowired
	private SalesPlanMapper salesPlanMapper;
	@Transactional(readOnly=true)
	public Set<SalesPlan> getById(Integer id) {
		return salesPlanMapper.getById(id);
	}
	@Transactional
	public void update(Integer id, String todo) {
		salesPlanMapper.update(id,todo);
	}
	@Transactional
	public void delete(Integer id) {
		salesPlanMapper.delete(id);
	}
	@Transactional
	public void save(Integer id, Date date, String todo) {
		salesPlanMapper.save(id,date,todo);
	}
	@Transactional
	public void saveResult(Integer id, String result) {
		salesPlanMapper.saveResult(id,result);
	}
}
