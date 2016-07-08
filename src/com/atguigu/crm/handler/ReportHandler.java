package com.atguigu.crm.handler;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.entity.CustomerDrain;
import com.atguigu.crm.mapper.ReportDrainMapper;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.ReportDrainService;
import com.atguigu.crm.service.ReportService;


@RequestMapping("/report")
@Controller
public class ReportHandler extends BaseHandler {
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private ReportDrainService reportDrainService;

	
	@RequestMapping("/drain/list")
	public String toDrain(@RequestParam(value="pageNo",required=false,defaultValue="1") Integer pageNo,
							Map<String,Object> map,
							HttpServletRequest request){
		
		Map<String,Object> params=WebUtils.getParametersStartingWith(request, "search_");
								
		String queryString = encodeParamsToQueryString(params);
		
		map.put("queryString", queryString);
		
		Page<CustomerDrain> page=reportDrainService.getPage(pageNo, params);
		
		map.put("page", page);
		
		return "report/drain";
	}
	
	@RequestMapping("/pay/list")
	public String toPay(@RequestParam(value="pageNo",required=false,defaultValue="1") Integer pageNo,
										Map<String,Object> map,
											HttpServletRequest request){
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		String queryString = encodeParamsToQueryString(params);
		System.out.println(queryString);
		map.put("queryString", queryString);
		
		Page<Object[]> page=reportService.getPage(pageNo, params);
		
		List<Object[]> content = page.getContent();
		
		request.getSession().setAttribute("content",content);
		
		System.out.println(page.getContent());
		
		map.put("page", page);
		
		return "report/pay";
	}
	
}
