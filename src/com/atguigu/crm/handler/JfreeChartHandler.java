package com.atguigu.crm.handler;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JfreeChartHandler {

	
	/*[{name=魅族科技}, {name=阿拉灯, totalMoney=120000}, {name=联想移动}]*/
			
	@RequestMapping("/JfreeChart")
	public String toJfreeChart(Map<String,Object> map){
		
		/*List<Object[]> content=(List<Object[]>) session.getAttribute("content");
		
		for (int i = 0; i < content.size(); i++) {
			Object[] objects = content.get(i);
		}*/
		map.put("魅族科技", 20);
		map.put("阿拉灯", 30);
		map.put("联想移动", 50);
		
		return "success";
	}
	
}
