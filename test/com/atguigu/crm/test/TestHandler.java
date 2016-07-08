package com.atguigu.crm.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestHandler {

	@ResponseBody
	@RequestMapping("/addNew")
	public String addNew(@RequestParam("date") String date,
			@RequestParam("todo") String todo){
		System.out.println("date" + date + ",todo:" + todo);
		
		//·µ»Ø id. 
		return "" + 1004;
	}
	
	@ResponseBody
	@RequestMapping("/testAjax")
	public String testAjax(@RequestParam("id") Integer id,
			@RequestParam("todo") String todo){
		System.out.println("id:" + id + ",todo:" + todo);
		return "1";
	}
	
}
