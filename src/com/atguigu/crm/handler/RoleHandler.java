package com.atguigu.crm.handler;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.atguigu.crm.entity.Authority;
import com.atguigu.crm.entity.Role;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.AuthService;
import com.atguigu.crm.service.RoleService;

@RequestMapping("/role")
@Controller
public class RoleHandler extends BaseHandler {

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private AuthService authService;
	
	@RequestMapping(value="/assign",method=RequestMethod.PUT)
	public String batchSave(Role role,RedirectAttributes attributes){
		roleService.batchSave(role);
		attributes.addFlashAttribute("message","操作成功！");
		return "redirect:/role/list2";
	}
	
	
	@RequestMapping(value="/assign/{id}",method=RequestMethod.GET)
	public String toAssign(@PathVariable("id") Integer id,Map<String,Object> map){
		map.put("role", roleService.getById(id));
		map.put("parentAuthorities", authService.getParentAuthorities());
		
		return "role/assign";
	}
	
	@RequestMapping("/delete-ajax")
	public void delete(@RequestParam("id") Integer id,HttpServletResponse reponse) throws IOException{
		roleService.delete(id);
		reponse.getWriter().write("1");
	}
	
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public String saveOrUpdate(Map<String,Object> map,
			Role role,
			RedirectAttributes attributes){
		if(role.getId()==null){
			attributes.addFlashAttribute("message","新建成功！");
			roleService.save(role);
		}else{
			attributes.addFlashAttribute("message","修改成功！");
			roleService.update(role);
		}
		return "redirect:/role/list2";
	}
	
	@RequestMapping(value="/create",method=RequestMethod.GET)
	public String createOrUpdate(Map<String,Object> map,
						@RequestParam(value="id",required=false) Integer id){
		if(id!=null){
			Role role=roleService.getById(id);
			map.put("role", role);
		}else{
			map.put("role", new Role());
		}
		return "role/input";
	}
	
	@RequestMapping(value="/list2")
	public String list(@RequestParam(value="pageNo",required=false,defaultValue="1") Integer pageNo,
						Map<String,Object> map){
		Page<Role> page=roleService.getPage(pageNo);
		map.put("page",page);
		return "role/list";
	}
}
