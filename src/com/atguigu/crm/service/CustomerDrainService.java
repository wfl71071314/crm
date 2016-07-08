package com.atguigu.crm.service;

import java.util.Date;

import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.mapper.CustomerDrainMapper;

/**
 * 客户的状态分为:
 * 1. 正常
 * 2. 流失预警: 某客户连续 6 个月和公司没有任何业务往来. 则改客户应该有 "正常" 状态变为 "流失预警" 状态。 
 * 3. 流失
 * 4. 删除. 
 * 
 * 当点击 "客户流失管理" 的链接时, 在列表中显示: 流失预警状态的客户 和 已经流失的客户.
 * 
 * 客户什么时候由 "正常" 变为 "流失预警" 装太多的
 * 1. 当每次点击 "客户流失管理" 的链接时, 都去查询数据库, 看哪些客户需要变为流失预警状态. 若需要变, 则改变其状态。 
 * 2. 实际上更好的方案是: 在每天的指定的时间, 检查需要变为流失预警状态的客户, 并把其变为流失预警状态. 
 * 
 * 需要使用 quartz 来完成对 "正常" 状态的客户转换为 "流失预警" 的客户
 * 
 * 如何在 Spring 中使用 Quartz.
 * 1). 加入 jar 包.
 * 2). Spring 集成 Quartz. 查看 Spring 的帮助文档. 
 * 
 * mybatis 如何来调用存储过程. 
 * 1). 参看 CallableStatement 的文档: {call <procedure-name>[(<arg1>,<arg2>, ...)]
 * @Update("{call check_drain()}")
 * public void callCheckDrainProcedure();
 * 
 */
@Service
public class CustomerDrainService {

	@Autowired
	private CustomerDrainMapper customerDrainMapper;
	
	@Transactional
	public void callCheckDrainProcedure(){
		customerDrainMapper.callCheckDrainProcedure();
	}
	
}
