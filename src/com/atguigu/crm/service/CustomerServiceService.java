package com.atguigu.crm.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.crm.mapper.CustomerServiceMapper;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.entity.Customer;
import com.atguigu.crm.entity.CustomerService;

@Service
public class CustomerServiceService extends BaseService<CustomerService> {
	
	@Autowired
	private CustomerServiceMapper customerServiceMapper;

	public void create(CustomerService customerService) {
		customerServiceMapper.create(customerService);
	}

	public List<Customer> getAll() {
		return customerServiceMapper.getAll();
	}

	public void delete(Long id) {
		customerServiceMapper.delete(id);
	}


	public void allot(Integer id, Integer allotId, Date allotDate) {
		customerServiceMapper.allot(id,allotId,allotDate);
	}

	public CustomerService getById(Integer id) {
		return customerServiceMapper.getById(id);
	}

	public void deal(Long id, String serviceDeal, Date dealDate,
			String serviceType) {
		customerServiceMapper.deal(id,serviceDeal,dealDate,serviceType);
	}

	public void feedBack(Long id, String dealResult, String satisfy) {
		customerServiceMapper.feedBack(id,dealResult,satisfy);
	}

}
