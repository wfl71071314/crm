package com.atguigu.crm.handler;

import java.io.IOException;
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

import com.atguigu.crm.entity.Dict;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.DictService;


@RequestMapping("/dict")
@Controller
public class DictHandler extends BaseHandler{
	
	@Autowired
	private DictService dictService;
	
	@RequestMapping("/update")
	public String toUpdate(Map<String,Object> map,@RequestParam("id") Integer id){
		Dict dict=dictService.getById(id);
		map.put("dict", dict);
		return "dict/input";
	}
	
	@RequestMapping("/delete-ajax")
	public void delete(@RequestParam("id") Integer id,HttpServletResponse response) throws IOException{
		dictService.delete(id);
		response.getWriter().write("1");
	}
	
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public String create(Dict dict,RedirectAttributes attributes){
		if(dict.getId()==null){
			dictService.save(dict);
			attributes.addFlashAttribute("message", "新建成功！");
		}else{
			dictService.update(dict);
			attributes.addFlashAttribute("message", "修改成功！");
		}
		return "redirect:/dict/list2";
	}
	
	@RequestMapping(value="/create",method=RequestMethod.GET)
	public String toCreate(Map<String,Object> map){
		map.put("dict",new Dict());
		return "dict/input";
	}
	
	@RequestMapping(value="/list")
	public String query(Map<String,Object> map,
						@RequestParam(value="pageNo",required=false,defaultValue="1") Integer pageNo,
						HttpServletRequest request){
		
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		String queryString = encodeParamsToQueryString(params);
		System.out.println(queryString);
		map.put("queryString", queryString);
		
		Page<Dict> page=dictService.getPage(pageNo, params);
		
		
		map.put("page", page);
		return "dict/list";
	}
	
	@RequestMapping(value="/list2",method=RequestMethod.GET)
	public String list2(Map<String,Object> map,
						@RequestParam(value="pageNo",required=false,defaultValue="1") Integer pageNo){
		Page<Dict> page=dictService.getPage2(pageNo);
		map.put("page", page);
		return "dict/list";
	}
}
