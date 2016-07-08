package com.atguigu.crm.handler;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.entity.CustomerDrain;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.CustomerServices;
import com.atguigu.crm.service.DrainService;


@RequestMapping("/drain")
@Controller
public class DrainHandler extends BaseHandler {

	@Autowired
	private DrainService drainService;
	
	@Autowired
	private CustomerServices customerService;
	
	
	@RequestMapping(value="/confirm",method=RequestMethod.POST)
	public String confirm(Map<String,Object> map,@RequestParam("id") Long id,
			@RequestParam(value="reason",required=false) String reason,
			RedirectAttributes attributes) throws ParseException{
		
		
		attributes.addFlashAttribute("message","操作成功！");
		
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = sdf.format(date);
		
		Date confirmDate = sdf.parse(format);
		
		drainService.confirm(id,reason,confirmDate);
		
		customerService.confirm(id);
		
		return "redirect:/drain/list2";
	}
	
	@RequestMapping(value="/confirm",method=RequestMethod.GET)
	public String ToConfirm(Map<String,Object> map,@RequestParam("id") Integer id){
		CustomerDrain drain=drainService.getById(id);
		map.put("drain", drain);
		return "drain/confirm";
	}
	
	@RequestMapping(value="/delay",method=RequestMethod.POST)
	public void delayMethod(@RequestParam("id") Integer id,
								@RequestParam("delay") String delay,
						Map<String,Object> map,HttpServletRequest request,HttpServletResponse response) throws IOException{
		CustomerDrain drain=drainService.getById(id);
		String string = drain.getDelay();
		StringBuilder sb=new StringBuilder(string);
		String newDelay = sb.append("`").append(delay).toString();
		
		drainService.delay(id,newDelay);
		String Referer = request.getHeader("Referer");
		response.sendRedirect(Referer);
	}
	
	
	@RequestMapping(value="/delay",method=RequestMethod.GET)
	public String delay(@RequestParam("id") Integer id,
						Map<String,Object> map){
		CustomerDrain drain=drainService.getById(id);
		map.put("drain", drain);
		return "drain/delay";
	}
	
	@RequestMapping(value="/list2")
	public String List(@RequestParam(value="pageNo",required=false,defaultValue="1") Integer pageNo,
						Map<String,Object> map,HttpServletRequest request){
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		
		String queryString = encodeParamsToQueryString(params);
		map.put("queryString", queryString);
		
		Page<CustomerDrain> page=drainService.getPage(pageNo,params);
		map.put("page", page);
		
		return "drain/list";
	}
	
}
