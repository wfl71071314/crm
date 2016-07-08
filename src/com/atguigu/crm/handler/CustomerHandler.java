package com.atguigu.crm.handler;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.entity.Customer;
import com.atguigu.crm.entity.Dict;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.CustomerServices;


@RequestMapping("/customer")
@Controller
public class CustomerHandler {
	
	@Autowired
	private CustomerServices customerService;
	
	
	private String encodeParamsToQueryString(Map<String, Object> params) {
		StringBuilder result = new StringBuilder();
		
		for(Map.Entry<String, Object> entry: params.entrySet()){
			String key = entry.getKey();
			Object val = entry.getValue();
			
			if(val == null || val.toString().trim().equals("")){
				continue;
			}
			
			result.append("&")
			      .append("search_")
			      .append(key)
			      .append("=")
			      .append(val);
		}
		
		return result.toString();
	}
	
	@RequestMapping(value="/list")
	public String query(Map<String,Object> map,
						@RequestParam(value="pageNo",required=false,defaultValue="1") Integer pageNo,
						HttpServletRequest request){
		
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		String queryString = encodeParamsToQueryString(params);
		System.out.println(queryString);
		map.put("queryString", queryString);
		Page<Customer> page=customerService.getPage(pageNo, params);
		String type="地区";
		
		List<String> regions=customerService.getRegions(type);
		
		type="客户等级";
		List<String> levels=customerService.getLevels(type);
		
		map.put("page", page);
		map.put("regions", regions);
		map.put("levels", levels);
		return "customer/list";
	}
	
	@RequestMapping(value="/list2",method=RequestMethod.GET)
	public String list(Map<String,Object> map,
						@RequestParam(value="pageNo",required=false) Integer pageNo){
		
		Page<Customer> page=customerService.getPage(pageNo);
		
		String type="地区";
		List<String> regions=customerService.getRegions(type);
		
		type="客户等级";
		List<String> levels=customerService.getLevels(type);
		
		map.put("page", page);
		map.put("regions", regions);
		map.put("levels", levels);
		return "customer/list";
	}

}
