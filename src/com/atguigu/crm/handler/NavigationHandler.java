package com.atguigu.crm.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.crm.entity.Authority;
import com.atguigu.crm.entity.User;
import com.atguigu.crm.orm.Navigation;
//2. 在 Handler 方法中根据登录用户的权限-父权限的对应关系来构建 Navigation 的集合
@Controller
public class NavigationHandler {

	@RequestMapping("/shiro-navigation")
	@ResponseBody
	public List<Navigation> getNavigation(HttpSession session){
		
		User user=(User) session.getAttribute("user");
		
		List<Navigation> navigations=new ArrayList<>();
		
		String contextPath=session.getServletContext().getContextPath();
		
		Navigation top=new Navigation(Long.MAX_VALUE,"客户关系管理系统");
		
		navigations.add(top);
		
		Map<Long,Navigation> parentNavigations=new HashMap<>();
		
		List<Authority> userAuthorities = user.getRole().getAuthorities();
		
		for (Authority authority : userAuthorities) {
			Navigation navigation=new Navigation(authority.getId(),authority.getDisplayName());
			navigation.setUrl(contextPath+authority.getUrl());
			
			Authority parentAuthority = authority.getParentAuthority();
			Navigation parentNavigation = parentNavigations.get(parentAuthority.getId());
			if(parentNavigation==null){
				parentNavigation=new Navigation(parentAuthority.getId(),parentAuthority.getDisplayName());
				parentNavigation.setState("closed");
				
				parentNavigations.put(parentAuthority.getId(), parentNavigation);
				top.getChildren().add(parentNavigation);
			}
			parentNavigation.getChildren().add(navigation);
		}
		
		return navigations;
	}
}
