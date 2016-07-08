/**
 * 
 */
package com.atguigu.crm.handler;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.entity.SalesChance;
import com.atguigu.crm.entity.User;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.SalesChanceService;
import com.atguigu.crm.service.UserService;

/**
 * @author wang fu liang 
 *
 */
@RequestMapping("/chance")
@Controller
public class SalesChanceHandler {

	@Autowired
	private SalesChanceService salesChanceService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/dispatch",method=RequestMethod.POST)
	public String dispatch(SalesChance salesChance
							,RedirectAttributes attributes,
							@RequestParam("designee.id") Integer did){
		salesChance.setStatus(2);
		User designee=userService.getById(did);
		salesChance.setDesignee(designee);
		salesChanceService.dispatch(salesChance);
		attributes.addFlashAttribute("message", "指派成功！");
		return "redirect:/chance/list";
	}	
	
	@RequestMapping(value="/dispatch/{id}",method=RequestMethod.GET)
	public String toDispatch(Map<String, Object> map,
							@PathVariable("id") Integer id){
		SalesChance chance = salesChanceService.getById(id);
		map.put("chance", chance);
		
		List<User> users=userService.getAll();
		map.put("users", users);
		
		return "chance/dispatch";
	}	
	@RequestMapping(value="/list")
	public String list(@RequestParam(value="pageNo", required=false,defaultValue="1") String pageNoStr,
			Map<String, Object> map,
			HttpServletRequest request){
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		String queryString = encodeParamsToQueryString(params);
		map.put("queryString", queryString);
		
		//1. 获取 pageNo
		int pageNo = 1;
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {}
		//2. 获取当前的登录用户
		User createBy = (User) request.getSession().getAttribute("user");
		params.put("EQO_createBy", createBy);
		params.put("EQI_status", 1);
		
		//3. 调用 Service 方法获取 Page 对象
		Page<SalesChance> page = salesChanceService.getPage(pageNo, params);
		//4. 把 Page 对象加入到 request 域对象中
		map.put("page", page);
		//5. 转发页面
		return "chance/list";
	}
	

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
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public String update(@PathVariable("id") Long id,SalesChance chance,
			@RequestParam(value="pageNo",required=false,defaultValue="1") Integer pageNo,
			RedirectAttributes attributes){
		chance.setId(id);
		salesChanceService.update(chance);
		attributes.addFlashAttribute("message", "更新成功！");
		return "redirect:/chance/list?pageNo="+pageNo;
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public String toUpdate(@PathVariable("id") Integer id,Map<String,Object> map){
		SalesChance salesChance=salesChanceService.getById(id);
		map.put("chance", salesChance);
		return "chance/input";
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public String delete(@PathVariable("id") Integer id,
			@RequestParam(value="id",required=false,defaultValue="1") Integer pageNo,
			RedirectAttributes attributes){
		
		attributes.addFlashAttribute("message","删除成功！");
		salesChanceService.delete(id);
		return "redirect:/chance/list?pageNo="+pageNo;
	}
	
	@RequestMapping(value="/",method=RequestMethod.POST)
	public String save(SalesChance salesChance,RedirectAttributes attributes){
		salesChance.setStatus(1);
		salesChanceService.save(salesChance);
		attributes.addFlashAttribute("message", "操作成功！");
		return "redirect:/chance/list";
	}
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String toInput(Map<String,Object> map){
		map.put("chance",new SalesChance());
		return "chance/input";
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String getPage(Map<String,Object> map
			,HttpSession session
			,@RequestParam(value="pageNo",required=false) String pageNoStr
			){
		int pageNo=1;
		try{
			pageNo=Integer.parseInt(pageNoStr);
		}catch(Exception e){}
		User createBy=(User)session.getAttribute("user");
		Page<SalesChance> page=salesChanceService.getPage(pageNo,createBy,1);
		map.put("page", page);
		return "chance/list";
	}
	
}
