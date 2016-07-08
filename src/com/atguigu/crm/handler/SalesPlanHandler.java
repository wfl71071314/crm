package com.atguigu.crm.handler;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.entity.SalesChance;
import com.atguigu.crm.entity.SalesPlan;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.SalesChanceService;
import com.atguigu.crm.service.SalesPlanService;

@Controller
@RequestMapping("/plan")
public class SalesPlanHandler extends BaseHandler{

	@Autowired
	private SalesPlanService salesPlanService;

	@Autowired
	private SalesChanceService salesChanceService;
	
	
	@RequestMapping("/execute")
	public void saveResult(@RequestParam("id") Integer id,
			@RequestParam("result") String result, HttpServletRequest request,
			HttpServletResponse response
			) throws IOException {
		//http://localhost:8080/crm/plan/make/4
		salesPlanService.saveResult(id, result);
		String Referer = request.getHeader("Referer");
		response.sendRedirect(Referer);
	}

	@RequestMapping("/execution/{id}")
	public String exec(@PathVariable("id") Integer id, Map<String, Object> map) {

		SalesChance chance = salesChanceService.getById(id);

		Set<SalesPlan> plan = salesPlanService.getById(id);

		chance.setSalesPlans(plan);

		map.put("chance", chance);

		return "plan/exec";
	}

	
	  @RequestMapping(value="/save",method=RequestMethod.POST) 
	  public void save(@RequestParam("chance.id") Integer id,
			  		   @RequestParam("todo") String todo,
			  		   @RequestParam("date") String date,
			  			 HttpServletRequest request,
			  			 HttpServletResponse response) throws IOException, ParseException{
		  Date newDate=new SimpleDateFormat("yyyy-MM-dd").parse(date);
		  salesPlanService.save(id, newDate, todo);
		  String referer = request.getHeader("Referer");
		  response.sendRedirect(referer);
	  }
	  
	 
	 
	@RequestMapping("/delete-ajax")
	public void delete(@RequestParam("id") Integer id,
			HttpServletResponse response) throws IOException {
		salesPlanService.delete(id);
		response.getWriter().write("1");
	}

	@RequestMapping("/make-ajax")
	public void update(@RequestParam("id") Integer id,
			@RequestParam("todo") String todo, HttpServletResponse response)
			throws IOException {
		salesPlanService.update(id, todo);
		response.getWriter().write("1");
	}

	@RequestMapping(value = "/make/{chanceId}", method = RequestMethod.GET)
	public String toMake(Map<String, Object> map,
			@PathVariable("chanceId") Integer id) {
		SalesChance chance = salesChanceService.getById(id);

		Set<SalesPlan> plan = salesPlanService.getById(id);

		chance.setSalesPlans(plan);

		map.put("chance", chance);

		return "plan/make";
	}
}
