package com.atguigu.crm.handler;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.entity.Customer;
import com.atguigu.crm.entity.CustomerService;
import com.atguigu.crm.entity.User;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.CustomerServiceService;
import com.atguigu.crm.service.CustomerServices;
import com.atguigu.crm.service.DictService;
import com.atguigu.crm.service.UserService;

@Controller
@RequestMapping("/service")
public class ServiceHandler extends BaseHandler{

	@Autowired
	private DictService dictService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomerServices customerServices;
	
	@Autowired
	private CustomerServiceService customerServiceService;
	
	
	@RequestMapping("/archive/{id}")
	public String archiveList(Map<String,Object> map,@PathVariable("id") Integer id){
		
		CustomerService service = customerServiceService.getById(id);
		map.put("service", service);
		
		return "service/archive/archive";
	}
	
	
	@RequestMapping("/archive")
	public String toArchiveList(@RequestParam(value="pageNo",required=false,defaultValue="1") Integer pageNo,
								HttpServletRequest request,
								Map<String,Object> map){
		Map<String,Object> params=WebUtils.getParametersStartingWith(request, "search_");
		
		params.put("EQS_serviceState","已归档");
		
		String queryString = encodeParamsToQueryString(params);
		
		map.put("queryString", queryString);
		
		Page<CustomerService> page=customerServiceService.getPage(pageNo, params);
		
		map.put("page", page);
		
		return "service/archive/list";
	}
	
	@RequestMapping(value="/feedback/toFeedback")
	public String feedBack(@RequestParam("id") Long id,
			@RequestParam("dealResult") String dealResult,
			@RequestParam("satisfy") String satisfy,
			RedirectAttributes attributes){
		
		customerServiceService.feedBack(id,dealResult,satisfy);
		
		attributes.addFlashAttribute("message","处理成功！");
		
		return "redirect:/service/feedBack";
	}
	
	@RequestMapping(value="/feedback/{id}")
	public String feedBackList(Map<String,Object> map,@PathVariable("id") Integer id){
		CustomerService service = customerServiceService.getById(id);
		map.put("service", service);
		return "service/feedback/feedback";
	}
	
	
	@RequestMapping(value="/feedBack")
	public String toFeedBackList(@RequestParam(value="pageNo",required=false,defaultValue="1") Integer pageNo,
								HttpServletRequest request,
								Map<String,Object> map){
		Map<String,Object> params=WebUtils.getParametersStartingWith(request, "serach_");
		params.put("EQS_serviceState", "已处理");
		
		String queryString=encodeParamsToQueryString(params);
		map.put("queryString", queryString);
		
		Page<CustomerService> page=customerServiceService.getPage(pageNo, params);
		
		map.put("page", page);
		
		return "service/feedback/list";
	}
	
	@RequestMapping(value="/deal",method=RequestMethod.PUT)
	public String toDeal(@RequestParam("id") Long id,
						@RequestParam("serviceDeal") String serviceDeal,
						@RequestParam("dealDate") String date) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		Date dealDate = sdf.parse(date);
		String serviceState="已处理";
		
		customerServiceService.deal(id,serviceDeal,dealDate,serviceState);
		
		return "/service/deal/list";
	}
	
	@RequestMapping("/create")
	public String create(CustomerService customerService,RedirectAttributes attributes){
		customerService.setServiceState("新创建");
		customerServiceService.create(customerService);
		attributes.addFlashAttribute("message","操作成功！");
		return "redirect:/service/allot";
	}
	
	@RequestMapping("/delete-ajax")
	public void delete(@RequestParam("id") Long id,HttpServletResponse response) throws IOException{
		customerServiceService.delete(id);
		response.getWriter().write("1");
	}
	
	@RequestMapping("/deal/{id}")
	public String deal(Map<String,Object> map,@PathVariable("id") Integer id){
		
		CustomerService service=customerServiceService.getById(id);
		
		map.put("service", service);
		
		return "/service/deal/deal";
	}
	@RequestMapping("/deal")
	public String toDeal(@RequestParam(value="pageNo",required=false,defaultValue="1") Integer pageNo,
						Map<String,Object> map,HttpServletRequest request){
		Map<String,Object> params=WebUtils.getParametersStartingWith(request,"search_");
		
		params.put("EQS_serviceState","已分配");
		
		String queryString=encodeParamsToQueryString(params);
		
		map.put("queryString", queryString);
		
		Page<CustomerService> page=customerServiceService.getPage(pageNo,params);
		
		map.put("page", page);
		
		return "/service/deal/list";
	}

	@RequestMapping(value="/allot-ajax",method=RequestMethod.POST)
	public void allot(@RequestParam(value="id") Integer id,
							@RequestParam(value="allotId") Integer allotId,
							HttpServletResponse response) throws IOException, ParseException{
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = sdf.format(date);
		Date allotDate = sdf.parse(format);
		customerServiceService.allot(id,allotId,allotDate);
		
		response.getWriter().write("1");
	}
	
	@RequestMapping(value="/allot")
	public String toAllot(@RequestParam(value="pageNo",required=false,defaultValue="1") Integer pageNo,
							Map<String,Object> map,HttpServletRequest request){
		
		Map<String,Object> params=WebUtils.getParametersStartingWith(request,"search_");
		
		String queryString=encodeParamsToQueryString(params);
		
		map.put("queryString", queryString);
		
		Page<CustomerService> page=customerServiceService.getPage(pageNo,params);
		
		map.put("page", page);
		
		List<User> users = userService.getAll();
		
		map.put("users", users);
		
		return "service/allot/list";
	}
	
	@RequestMapping("/input")
	public String toInput(Map<String,Object> map,HttpSession session){
		
		String type="服务类型";
		List<String> serviceTypes=dictService.getByType(type);
		
		List<Customer> customers=customerServices.getAll();
		User createdby=(User)session.getAttribute("user");
		
		map.put("serviceTypes", serviceTypes);
		map.put("customers", customers);
		map.put("createdby", createdby);
		map.put("customerService", new CustomerService());
		return "service/input";
	}
}
